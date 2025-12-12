/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.CheckSettings;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;

public class GeneratePermissionMandateFormRequest {

    @JsonProperty("driver")
    private Driver driver;
    @JsonProperty("check_settings")
    private CheckSettings checkSettings;

    public Driver getDriver() {
        return driver;
    }

    public CheckSettings getCheckSettings() {
        return checkSettings;
    }

}
