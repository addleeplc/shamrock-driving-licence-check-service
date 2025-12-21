package com.haulmont.shamrock.driving.licence.check.service;

import com.haulmont.monaco.ServiceException;
import com.haulmont.monaco.response.ErrorCode;
import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response.CheckedSafeResponse;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.*;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.*;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.MandateFormRequest;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.TokenRequest;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.UpdateUserStatusRequest;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response.MandateFormResponse;
import com.haulmont.shamrock.driving.licence.check.service.command.chacked_safe.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.UUID;

@Component
public class CheckedSafeService {

    @Inject
    private ServiceConfiguration serviceConfiguration;
    @Inject
    private Logger log;

    public void start() {
        this.token = _getToken();
        log.info("Checked safe token initialized. validUntil: {}", token.getValidUntil());
    }

    public Token _getToken() {
        var res = new GetTokenCommand(
                serviceConfiguration.getCheckedSafeCallbackToken(),
                new TokenRequest(
                        serviceConfiguration.getCheckedSafeUsername(),
                        serviceConfiguration.getCheckedSafePassword(),
                        serviceConfiguration.getCheckedSafeCallbackUrl()
                )
        ).execute();

        if (res.getStatus().equals(StatusCode.SUCCESS)) {
            return new Token(res.getApiToken().getToken(), res.getApiToken().getValidUntil());
        } else {
            log.error("Failed to obtain checked safe api token: {}", res.getError().getMessage());
            throw new ServiceException(ErrorCode.FAILED_DEPENDENCY, "Fail to call checked safe");
        }
    }

    private Token token;
    public synchronized String getToken() {
        if (DateTime.now().isAfter(token.getValidUntil().plusSeconds(10))) {
            this.token = _getToken();
            log.info("Checked safe token refreshed. validUntil: {}", token.getValidUntil());
        }

        return token.getToken();
    }

    public MandateFormResponse.PermissionMandateRequest requestMandateForm(UUID driverId, Driver driver, CheckSettings checkSettings) {
        var res = call(new RequestMandateFormCommand(
                getToken(),
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
        var res = call(new UpdateUserStatusCommand(
                getToken(),
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

    public LicencePermissionMandate getCompleteMandate(UUID driverId) {
        var res = call(new GetCompleteMandateCommand(getToken(), driverId));

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
        var res = call(new RequestPermissionMandateFormStatusCommand(getToken(), driverId));

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
        var res = call(new TriggerAdHocCheckCommand(getToken(), driverId));

        if (!isClientUserIdNotFound(res.getError()) && res.getStatus().equals(StatusCode.FAIL)) {
            log.error("Failed to trigger ad hoc check for driver: {}. ErrorMessage: : {}", driverId, res.getError().getMessage());
            throw new ServiceException(ErrorCode.FAILED_DEPENDENCY, "Fail to call checked safe");
        }
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

    public <C extends CheckedSafeCommand<R>, R extends CheckedSafeResponse> R call(C c) {
        R res = c.execute();
        if (res.getStatus().equals(StatusCode.FAIL) && res.getError().getMessage().equals("API Token has expired")) {
            log.info("Received api token expired error. Refreshing token");
            this.token = _getToken();
            c.setToken(token.getToken());
            return c.execute();
        } else {
            return res;
        }
    }

}
