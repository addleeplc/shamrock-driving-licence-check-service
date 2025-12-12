/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service;

import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.WorkingStatus;
import com.haulmont.shamrock.driving.licence.check.service.command.LoadDriverCommand;
import com.haulmont.shamrock.driving.licence.check.service.command.UpdateWorkingStatusCommand;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.DriverResponse;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;

import java.util.UUID;

import static com.haulmont.monaco.unirest.ServiceCallUtils.call;
import static com.haulmont.monaco.unirest.ServiceCallUtils.extract;

@Component
public class DriverRegistryService {

    @Inject
    private ServiceConfiguration serviceConfiguration;

    public void updateWorkingStatus(UUID driverId, WorkingStatus workingStatus) {
        call(() -> new UpdateWorkingStatusCommand(driverId, workingStatus, serviceConfiguration.getDriverRegistryApiKey()), response -> null);
    }

    public Driver loadDriver(UUID driverId) {
        return call(() -> new LoadDriverCommand(driverId), response -> extract(response, DriverResponse::getDriver));
    }
}
