/*
 * Copyright 2008 - 2022 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.mq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.monaco.mq.messages.AbstractMessage;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;

public class DriverUpdated extends AbstractMessage {

    @JsonProperty("data")
    private Data data;

    public DriverUpdated() {
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Data {

        @JsonProperty("driver")
        private Driver driver;

        public Data() {
        }

        public Driver getDriver() {
            return driver;
        }

        public void setDriver(Driver driver) {
            this.driver = driver;
        }
    }

}
