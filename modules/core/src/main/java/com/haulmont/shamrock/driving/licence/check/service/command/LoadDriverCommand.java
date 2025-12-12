package com.haulmont.shamrock.driving.licence.check.service.command;

import com.haulmont.monaco.unirest.UnirestCommand;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.DriverResponse;
import kong.unirest.HttpRequest;

import java.util.Collections;
import java.util.UUID;

public class LoadDriverCommand extends UnirestCommand<DriverResponse> {

    private static final String SERVICE = "shamrock-driver-registry-service";

    private final UUID driverId;

    public LoadDriverCommand(UUID driverId) {
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