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
import com.haulmont.shamrock.driving.licence.check.service.EmailService;
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
    private EmailService emailService;
    @Inject
    private DrivingLicenceRepository drivingLicenceRepository;
    @Inject
    private DriverRegistryService driverRegistryService;

    public String requestMandateForm(UUID driverId, Driver driver, CheckSettings checkSettings) {
        return checkedSafeService.requestMandateForm(driverId, driver, checkSettings).getPermissionMandateUrl();
    }

    public PermissionMandateForm getCompletedMandate(UUID driverId) {
        var res = checkedSafeService.getCompletedMandate(driverId);

        if (res == null) {
            var status = getMandateFormStatus(driverId);
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
        checkedSafeService.triggerAdHocCheck(driverId);
    }

    public MandateFormStatus getMandateFormStatus(UUID driverId) {
        var status = checkedSafeService.getMandateFormStatus(driverId);
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

    public void updateClientStatus(UUID driverId, ClientStatus status) {
        checkedSafeService.updateUserStatus(driverId, status);
    }

    public void handleLicenceCheck(LicenceCheck licenceCheck) {
        Driver driver = driverRegistryService.loadDriver(licenceCheck.getClientUserId());
        DrivingLicenceCheckCompleted shamrockEvent = convert(driver, licenceCheck);

        rabbitMqPublisher.publish(shamrockEvent);
    }

    public void handleLicenceCheckError(UUID driverId, CheckedSafeError error) {
        Driver driver = driverRegistryService.loadDriver(driverId);

        rabbitMqPublisher.publish(convert(driver, error));
    }
}
