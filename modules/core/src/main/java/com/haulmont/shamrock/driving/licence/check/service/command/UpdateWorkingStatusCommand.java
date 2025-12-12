/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command;

import com.haulmont.monaco.response.Response;
import com.haulmont.monaco.unirest.UnirestCommand;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.WorkingStatus;
import kong.unirest.HttpRequest;

import java.util.Collections;
import java.util.UUID;

public class UpdateWorkingStatusCommand extends UnirestCommand<Response>  {

    private static final String SERVICE = "shamrock-driver-registry-service";

    private final UUID driverId;
    private final WorkingStatus workingStatus;
    private final String apiKey;

    public UpdateWorkingStatusCommand(UUID driverId, WorkingStatus workingStatus, String apiKey) {
        super(SERVICE, Response.class);
        this.driverId = driverId;
        this.workingStatus = workingStatus;
        this.apiKey = apiKey;
    }

    @Override
    protected HttpRequest<?> createRequest(String url, Path path) {
        Driver driver = new Driver();
        driver.setStatus(workingStatus);
        return post(url, path)
                .header("Content-Type", "application/json")
                .header("X-API-Key", apiKey)
                .body(driver);
    }

    @Override
    protected Path getPath() {
        return new Path("/drivers/{driverId}/", Collections.singletonMap("driverId", driverId));
    }
}
