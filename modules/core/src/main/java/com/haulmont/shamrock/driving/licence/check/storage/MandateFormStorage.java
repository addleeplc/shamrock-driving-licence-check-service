/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.storage;

import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.CheckSettings;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;
import com.haulmont.shamrock.driving.licence.check.redis.RedisStorageBase;
import com.haulmont.shamrock.driving.licence.check.redis.key.SingleKey;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class MandateFormStorage extends RedisStorageBase<UUID, MandateFormContext, SingleKey<UUID>> {

    @Inject
    private ServiceConfiguration serviceConfiguration;

    public MandateFormStorage() {
        super(MandateFormContext.class, ServiceConfiguration::getRedisMandateFromTTL);
    }

    public void save(UUID driverId, Driver driver, CheckSettings checkSettings, String signingLink) {
        MandateFormContext mandateFormContext = new MandateFormContext(driverId, driver, checkSettings, signingLink);
        SingleKey<UUID> key = SingleKey.key(driverId);
        save(key, mandateFormContext,
                (long) (serviceConfiguration.getCheckedSafeMandateFormLinkExpiryMinutes() - serviceConfiguration.getRedisMandateFormInvalidateBeforeExpiryMinutes()) * 60
        );
    }

    public Optional<String> getSigningLink(UUID driverId,  Driver driver, CheckSettings checkSettings) {
        var cache = get(SingleKey.key(driverId));
        if (cache.isEmpty()) return Optional.empty();

        return isSame(cache.get(), driver, checkSettings)
                ? cache.map(MandateFormContext::getSigningLink)
                : Optional.empty();
    }

    private boolean isSame(MandateFormContext mandateFormContext, Driver driver, CheckSettings checkSettings) {
        return Objects.equals(mandateFormContext.getDriver(), driver)
                && Objects.equals(mandateFormContext.getCheckSettings(), checkSettings);
    }

}
