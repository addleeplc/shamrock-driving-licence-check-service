package com.haulmont.shamrock.driving.licence.check.service;

import com.haulmont.monaco.AppContext;
import com.haulmont.monaco.ServiceException;
import com.haulmont.monaco.redis.Redis;
import com.haulmont.monaco.redis.cache.RedisCacheObjectCodec;
import com.haulmont.monaco.redis.cache.codec.JacksonObjectCodec;
import com.haulmont.monaco.redis.cache.codec.PropertyObjectCodec;
import com.haulmont.monaco.response.ErrorCode;
import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response.*;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.*;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.*;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.MandateFormRequest;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.TokenRequest;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.UpdateUserStatusRequest;
import com.haulmont.shamrock.driving.licence.check.redis.LockRegistry;
import com.haulmont.shamrock.driving.licence.check.service.command.chacked_safe.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.UUID;

@Component
public class CheckedSafeService {
    public static final String API_TOKEN_REDIS_KEY_NAME = "token";
    public static final int API_TOKEN_REDIS_DEFAULT_TTL_SECONDS = 60;

    private static final RedisCacheObjectCodec<String> tokenRedisKeyCodec = new PropertyObjectCodec<>("checkedsafe", String.class, null);
    private static final  String API_TOKEN_REDIS_KEY = tokenRedisKeyCodec.encode(API_TOKEN_REDIS_KEY_NAME);

    private final RedisCacheObjectCodec<ApiToken> apiTokenRedisValueCodec = new JacksonObjectCodec<>(ApiToken.class);

    @Inject
    private ServiceConfiguration serviceConfiguration;
    @Inject
    private Logger log;

    @Inject
    private LockRegistry lockRegistry;

    public MandateFormResponse.PermissionMandateRequest requestMandateForm(UUID driverId, Driver driver, CheckSettings checkSettings) {
        MandateFormResponse res = call(new RequestMandateFormCommand(
                new MandateFormRequest(
                        driver.getDrivingLicence(),
                        formatName(driver),
                        driver.getDateOfBirth(),
                        Optional.of(driver)
                                .map(Driver::getHomeAddress)
                                .map(this::cropPostalCode)
                                .orElse(null),
                        Optional.of(driver)
                                .map(Driver::getHomeAddress)
                                .map(HomeAddress::getAddressComponents)
                                .map(AddressComponent::getPostalCode)
                                .orElse(null),
                        Optional.ofNullable(checkSettings)
                                .map(CheckSettings::getCpcEnabled)
                                .orElse(false),
                        Optional.ofNullable(checkSettings)
                                .map(CheckSettings::getTachographEnabled)
                                .orElse(false),
                        driverId,
                        serviceConfiguration.getCheckedSafeMandateFormLinkExpiryMinutes(),
                        serviceConfiguration.getCheckedSafeMandateFormSuccessRedirectUrl()
                )
        ));

        if (res.getStatus().equals(StatusCode.SUCCESS)) {
            return res.getPermissionMandateRequest();
        } else if (res.getError().getMessage().equals("User with same clientUserId but different name or surname found")) {
          throw new ServiceException(ErrorCode.BAD_REQUEST, "User with same driver id but different name or surname found");
        } else {
            log.error("Failed to request mandate form for driver: {}. ErrorMessage: {}", driverId, res.getError().getMessage());
            throw new ServiceException(ErrorCode.FAILED_DEPENDENCY, "Fail to call checked safe");
        }
    }

    public void updateUserStatus(UUID driverId, ClientStatus status) {
        CheckedSafeResponse res = call(new UpdateUserStatusCommand(
                new UpdateUserStatusRequest(
                        driverId,
                        status
                )
        ));

        if (res.getError() != null && !isClientUserIdNotFound(res.getError()) && res.getStatus().equals(StatusCode.FAIL)) {
            log.error("Failed to update user status for driver: {}. ErrorMessage: : {}", driverId, res.getError().getMessage());
            throw new ServiceException(ErrorCode.FAILED_DEPENDENCY, "Fail to call checked safe");
        }
    }

    public LicencePermissionMandate getCompletedMandate(UUID driverId) {
        CompletedMandateResponse res = call(new GetCompletedMandateCommand(driverId));

        if (res.getStatus().equals(StatusCode.SUCCESS)) {
            return res.getLicencePermissionMandateId();
        } else if (isClientUserIdNotFound(res.getError())) {
            return null;
        } else {
            log.error("Failed to get complete mandate for driver: {}. ErrorMessage: : {}", driverId, res.getError().getMessage());
            throw new ServiceException(ErrorCode.FAILED_DEPENDENCY, "Fail to call checked safe");
        }
    }

    public PermissionMandateFormStatus getMandateFormStatus(UUID driverId) {
        PermissionMandateFormStatusResponse res = call(new RequestPermissionMandateFormStatusCommand(driverId));

        if (res.getStatus().equals(StatusCode.SUCCESS)) {
            return res.getPermissionMandateFormStatus();
        } else if (isClientUserIdNotFound(res.getError())) {
            return null;
        } else {
            log.error("Failed to request mandatory form status for driver: {}. ErrorMessage: : {}", driverId, res.getError().getMessage());
            throw new ServiceException(ErrorCode.FAILED_DEPENDENCY, "Fail to call checked safe");
        }
    }

    public void triggerAdHocCheck(UUID driverId) {
        CheckedSafeResponse res = call(new TriggerAdHocCheckCommand(driverId));

        if (!isClientUserIdNotFound(res.getError()) && res.getStatus().equals(StatusCode.FAIL)) {
            log.error("Failed to trigger ad hoc check for driver: {}. ErrorMessage: : {}", driverId, res.getError().getMessage());
            throw new ServiceException(ErrorCode.FAILED_DEPENDENCY, "Fail to call checked safe");
        }
    }

    private ApiToken requestToken() {
        TokenResponse res = new GetTokenCommand(
                serviceConfiguration.getCheckedSafeCallbackToken(),
                new TokenRequest(
                        serviceConfiguration.getCheckedSafeUsername(),
                        serviceConfiguration.getCheckedSafePassword(),
                        serviceConfiguration.getCheckedSafeCallbackUrl()
                )
        ).execute();

        if (StatusCode.SUCCESS.equals(res.getStatus())) {
            return res.getApiToken();
        } else {
            String msg = res.getError() != null ? res.getError().getMessage() : null;
            log.error("Failed to obtain checked safe api token: {}", msg);
            throw new ServiceException(ErrorCode.FAILED_DEPENDENCY, "Fail to call checked safe");
        }
    }

    private <C extends CheckedSafeCommand<R>, R extends CheckedSafeResponse> R call(C command) {
        Optional<ApiToken> cachedToken = getToken();
        String cachedTokenValue = cachedToken.map(ApiToken::getToken).orElse(null);

        if(cachedToken.isPresent()) {
            command.setToken(cachedTokenValue);
            R response = command.execute();

            if(!isTokenRelatedError(response)) {
                return response;
            }
        }

        log.info("CheckedSafe API token is invalid. Acquiring distributed token lock to refresh it");

        return lockRegistry.withTokenLock(() -> {
            Optional<ApiToken> actualCachedToken = getToken();
            String actualCachedTokenValue = actualCachedToken.map(ApiToken::getToken).orElse(null);

            if(actualCachedTokenValue != null && !actualCachedTokenValue.equals(cachedTokenValue)) {
                command.setToken(actualCachedTokenValue);
                R retryResponse = command.execute();

                if(!isTokenRelatedError(retryResponse)) {
                    return retryResponse;
                }
            }

            ApiToken newToken = requestToken();
            saveToken(newToken);

            command.setToken(newToken.getToken());
            return command.execute();
        });
    }

    private void saveToken(ApiToken newToken) {
        getRedis().setex(API_TOKEN_REDIS_KEY, calculateTtlSeconds(newToken), apiTokenRedisValueCodec.encode(newToken));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isTokenRelatedError(CheckedSafeResponse response) {
        if (response == null || response.getStatus() == null || response.getError() == null) {
            return false;
        }
        return StatusCode.FAIL.equals(response.getStatus())
                && ("API Token has expired".equalsIgnoreCase(response.getError().getMessage())
                    || "API Token is missing".equalsIgnoreCase(response.getError().getMessage()));
    }

    private long calculateTtlSeconds(ApiToken apiToken) {
        DateTime validUntil = apiToken.getValidUntil();

        if (validUntil == null) {
            return API_TOKEN_REDIS_DEFAULT_TTL_SECONDS;
        }
        DateTime now = DateTime.now();
        if (!validUntil.isAfter(now)) {
            return API_TOKEN_REDIS_DEFAULT_TTL_SECONDS;
        }
        Duration duration = new Duration(now, validUntil);
        return Math.max(API_TOKEN_REDIS_DEFAULT_TTL_SECONDS, duration.getStandardSeconds());
    }
    
    private Optional<ApiToken> getToken() {
        String existingTokenStr = getRedis().get(API_TOKEN_REDIS_KEY);
        if (existingTokenStr != null) {
            return Optional.ofNullable(apiTokenRedisValueCodec.decode(existingTokenStr));
        }  else {
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    private Redis<String, String> getRedis() {
        return AppContext.getResources().get(serviceConfiguration.getRedisResource(), Redis.class);
    }

    private boolean isClientUserIdNotFound(CheckedSafeError error) {
        return error.getMessage().startsWith("No completed mandate found for clientUserId:")
                || error.getMessage().startsWith("No mandate form found for clientUserId:")
                || error.getMessage().equals("Mandate form not found")
                || error.getMessage().startsWith("User with clientUserId");
    }

    private String formatName(Driver driver) {
        if(StringUtils.isBlank(driver.getMiddleName())) {
            return driver.getFirstName() + " " + driver.getLastName();
        } else {
            return driver.getFirstName() + " " + driver.getMiddleName() + " " + driver.getLastName();
        }
    }

    private String cropPostalCode(HomeAddress address) {
        AddressComponent addressComponents = address.getAddressComponents();
        String formattedAddress = address.getFormattedAddress();

        if (addressComponents != null
                && StringUtils.isNotBlank(addressComponents.getPostalCode())
                && StringUtils.isNotBlank(formattedAddress)
                && formattedAddress.contains(addressComponents.getPostalCode())) {
            return formattedAddress.replace(", " + addressComponents.getPostalCode(), "");
        }

        return formattedAddress;
    }
}
