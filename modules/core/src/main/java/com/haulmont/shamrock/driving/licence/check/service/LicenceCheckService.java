/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service;

import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.ClientStatus;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.CheckSettings;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;
import com.haulmont.shamrock.driving.licence.check.dto.PermissionMandateForm;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.MandateFormStatus;
import com.haulmont.shamrock.driving.licence.check.storage.MandateFormStorage;
import org.joda.time.DateTime;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;

import java.util.UUID;

import static com.haulmont.shamrock.driving.licence.check.dto.checked_safe.MandateFormStatus.*;

@Component
public class LicenceCheckService {

    @Inject
    private CheckedSafeService checkedSafeService;
    @Inject
    private MandateFormStorage mandateFormStorage;

    public String requestMandateForm(UUID driverId, Driver driver, CheckSettings checkSettings) {
        return mandateFormStorage.getSigningLink(driverId, driver, checkSettings).orElseGet(() -> {
            var link = checkedSafeService.requestMandateForm(driverId, driver, checkSettings).getPermissionMandateUrl();
            mandateFormStorage.save(driverId, driver, checkSettings, link);
            return link;
        }
        );
    }

    public PermissionMandateForm getCompleteMandate(UUID driverId) {
        var res = checkedSafeService.getCompleteMandate(driverId);

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

}
