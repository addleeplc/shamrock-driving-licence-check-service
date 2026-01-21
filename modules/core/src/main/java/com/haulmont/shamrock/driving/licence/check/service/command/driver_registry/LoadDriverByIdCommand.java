/*
 * Copyright 2008 - 2026 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command.driver_registry;

import com.haulmont.monaco.unirest.UnirestCommand;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.DriverResponse;
import kong.unirest.HttpRequest;

import java.util.Collections;
import java.util.UUID;

public class LoadDriverByIdCommand extends UnirestCommand<DriverResponse> {

    private static final String SERVICE = "shamrock-driver-registry-service";

    private final UUID driverId;

    public LoadDriverByIdCommand(UUID driverId) {
        super(SERVICE, DriverResponse.class);
        this.driverId = driverId;
    }

    @Override
    protected HttpRequest<?> createRequest(String url, Path path) {
        return get(url, path);
    }

    @Override
    protected Path getPath() {
        return new Path("/drivers/{driver_id}", Collections.singletonMap("driver_id", driverId));
    }
}