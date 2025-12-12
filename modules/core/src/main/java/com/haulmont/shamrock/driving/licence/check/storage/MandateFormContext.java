/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.CheckSettings;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;

import java.util.UUID;

public class MandateFormContext {

    @JsonProperty("driver_id")
    private UUID driverId;
    @JsonProperty("driver")
    private Driver driver;
    @JsonProperty("check_setting")
    private CheckSettings checkSettings;
    @JsonProperty("signing_link")
    private String signingLink;

    public MandateFormContext() {
    }

    public MandateFormContext(UUID driverId, Driver driver, CheckSettings checkSettings, String signingLink) {
        this.driverId = driverId;
        this.driver = driver;
        this.checkSettings = checkSettings;
        this.signingLink = signingLink;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public void setDriverId(UUID driverId) {
        this.driverId = driverId;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public CheckSettings getCheckSettings() {
        return checkSettings;
    }

    public void setCheckSettings(CheckSettings checkSettings) {
        this.checkSettings = checkSettings;
    }

    public String getSigningLink() {
        return signingLink;
    }

    public void setSigningLink(String signingLink) {
        this.signingLink = signingLink;
    }
}
