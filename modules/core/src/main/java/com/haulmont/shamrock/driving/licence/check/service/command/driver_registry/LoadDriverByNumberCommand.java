/*
 * Copyright 2008 - 2026 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command.driver_registry;

import com.haulmont.monaco.unirest.UnirestCommand;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.DriversResponse;
import kong.unirest.HttpRequest;

import java.util.Collections;

public class LoadDriverByNumberCommand extends UnirestCommand<DriversResponse> {

    private static final String SERVICE = "shamrock-driver-registry-service";

    private final String number;

    public LoadDriverByNumberCommand(String number) {
        super(SERVICE, DriversResponse.class);
        this.number = number;
    }

    @Override
    protected HttpRequest<?> createRequest(String url, Path path) {
        return get(url, path)
                .queryString("number", number);
    }

    @Override
    protected Path getPath() {
        return new Path("/drivers/search", Collections.emptyMap());
    }
}