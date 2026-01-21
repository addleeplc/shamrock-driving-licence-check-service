/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check;

import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.CheckedSafeError;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.ClientStatus;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook.LicenceCheck;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.CheckSettings;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;
import com.haulmont.shamrock.driving.licence.check.dto.PermissionMandateForm;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.MandateFormStatus;
import com.haulmont.shamrock.driving.licence.check.mq.RabbitMqPublisher;
import com.haulmont.shamrock.driving.licence.check.mq.dto.DrivingLicenceCheckCompleted;
import com.haulmont.shamrock.driving.licence.check.service.CheckedSafeService;
import com.haulmont.shamrock.driving.licence.check.service.DriverRegistryService;
import org.joda.time.DateTime;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;

import java.util.UUID;

import static com.haulmont.shamrock.driving.licence.check.dto.checked_safe.MandateFormStatus.*;
import static com.haulmont.shamrock.driving.licence.check.utils.EventConverter.convert;

@Component
public class LicenceCheckService {

    @Inject
    private CheckedSafeService checkedSafeService;

    @Inject
    private RabbitMqPublisher rabbitMqPublisher;
    @Inject
    private DriverRegistryService driverRegistryService;

    public String requestMandateForm(Driver driver, CheckSettings checkSettings) {
        return checkedSafeService.requestMandateForm(driver, checkSettings).getPermissionMandateUrl();
    }

    public PermissionMandateForm getCompletedMandate(UUID driverId) {
        var driver = driverRegistryService.loadDriver(driverId);
        var res = checkedSafeService.getCompletedMandate(driver.getNumber());

        if (res == null) {
            var status = _getMandateFormStatus(driver.getNumber());
            return new PermissionMandateForm(status);
        }

        MandateFormStatus status;
        if (res.getExpiresOn().isBefore(DateTime.now())) {
            status = EXPIRED;
        } else {
            status = SIGNED;
        }

        return new PermissionMandateForm(
                res.getPermissionMandatePdf(),
                res.getCompletedOn(),
                res.getExpiresOn(),
                status
        );
    }

    public void triggerAdHocCheck(UUID driverId) {
        var driver = driverRegistryService.loadDriver(driverId);
        checkedSafeService.triggerAdHocCheck(driver.getNumber());
    }

    public MandateFormStatus getMandateFormStatus(UUID driverId) {
        var driver = driverRegistryService.loadDriver(driverId);
        return _getMandateFormStatus(driver.getNumber());
    }

    private MandateFormStatus _getMandateFormStatus(String driverNumber) {
        var status = checkedSafeService.getMandateFormStatus(driverNumber);
        if (status == null) {
            return NOT_GENERATED;
        } else if (!status.getMandateFormCompleted() && status.getExpired()) {
            return MandateFormStatus.EXPIRED;
        } else if (status.getMandateFormCompleted()) {
            return MandateFormStatus.SIGNED;
        } else if (!status.getMandateFormCompleted() && !status.getExpired()) {
            return MandateFormStatus.AWAITING;
        }

        return null;
    }

    public void updateClientStatus(String driverNumber, ClientStatus status) {
        checkedSafeService.updateUserStatus(driverNumber, status);
    }

    public void handleLicenceCheck(LicenceCheck licenceCheck) {
        Driver driver = driverRegistryService.loadDriver(licenceCheck.getClientUserId());
        DrivingLicenceCheckCompleted shamrockEvent = convert(driver, licenceCheck);

        rabbitMqPublisher.publish(shamrockEvent);
    }

    public void handleLicenceCheckError(String driverNumber, CheckedSafeError error) {
        Driver driver = driverRegistryService.loadDriver(driverNumber);

        rabbitMqPublisher.publish(convert(driver, error));
    }
}
