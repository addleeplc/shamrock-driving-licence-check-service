/*
 * Copyright (c) 2016 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */
package com.haulmont.shamrock.driving.licence.check.dto.driver_registry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.monaco.response.ErrorCode;
import com.haulmont.monaco.response.Response;

import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriversResponse extends Response {

    @JsonProperty("drivers")
    private Collection<Driver> drivers;

    public Collection<Driver> getDrivers() {
        return drivers;
    }
}
