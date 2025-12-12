/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service;

import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook.LicenceCheck;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.WorkingStatus;
import com.haulmont.shamrock.driving.licence.check.mybatis.SqlSessionFactory;
import com.haulmont.shamrock.driving.licence.check.service.command.UpdateDriverProfileCommand;
import org.joda.time.DateTime;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;

import java.util.UUID;

import static com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook.LicenceStatus.DISQUALIFIED;
import static com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook.LicenceStatus.EXPIRED;

@Component
public class DriverProfileService {

    @Inject
    private SqlSessionFactory sqlSessionFactory;
    @Inject
    private DriverRegistryService driverRegistryService;
    @Inject
    private EmailService emailService;
    @Inject
    private ServiceConfiguration serviceConfiguration;

    public void updateProfile(LicenceCheck licenceCheck) {
        Driver driver = driverRegistryService.loadDriver(licenceCheck.getClientUserId());
        if (licenceCheck.getLicenceStatus().equals(EXPIRED)) {
            changeWorkingStatus(licenceCheck.getClientUserId(),
                    serviceConfiguration.getLicenceCheckIncorrectWorkingStatus(),
                    serviceConfiguration.getLicenceCheckIncorrectWorkingStatusReason());
            emailService.sendIncorrectCheckMail(String.format("Driver '%s' (call sign) has not updated their driving licence which has now expired.", driver.getCallsign()));
            return;
        } else if (licenceCheck.getLicenceStatus().equals(DISQUALIFIED)) {
            changeWorkingStatus(licenceCheck.getClientUserId(),
                    serviceConfiguration.getLicenceCheckIncorrectWorkingStatus(),
                    serviceConfiguration.getLicenceCheckIncorrectWorkingStatusReason());
            emailService.sendDeclinedCheckMail(String.format("Driver '%s' (call sign) has licence which has now disqualified.", driver.getCallsign()));
            return;
        }

        new UpdateDriverProfileCommand(
                sqlSessionFactory,
                serviceConfiguration.getShamrockUserId(),
                licenceCheck.getClientUserId(),
                licenceCheck.getNumberOfPoints(),
                DateTime.now(),
                licenceCheck.getConvictions()
        ).execute();

        changeWorkingStatus(licenceCheck.getClientUserId(),
                serviceConfiguration.getLicenceCheckApprovedWorkingStatus(),
                serviceConfiguration.getLicenceCheckApprovedWorkingStatusReason());
    }

    private void changeWorkingStatus(UUID driverId, String code, String reason) {
        driverRegistryService.updateWorkingStatus(
                driverId,
                new WorkingStatus(
                        code,
                        reason
                )
        );
    }

}
