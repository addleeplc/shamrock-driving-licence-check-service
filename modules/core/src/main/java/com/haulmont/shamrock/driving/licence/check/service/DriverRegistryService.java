/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service;

import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.WorkingStatus;
import com.haulmont.shamrock.driving.licence.check.service.command.driver_registry.LoadDriverByIdCommand;
import com.haulmont.shamrock.driving.licence.check.service.command.driver_registry.LoadDriverByNumberCommand;
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
        return call(() -> new LoadDriverByIdCommand(driverId), response -> extract(response, DriverResponse::getDriver));
    }

    public Driver loadDriver(String number) {
        return call(() -> new LoadDriverByNumberCommand(number), response -> extract(response, it -> it.getDrivers().stream().findAny().orElse(null)));
    }
}
