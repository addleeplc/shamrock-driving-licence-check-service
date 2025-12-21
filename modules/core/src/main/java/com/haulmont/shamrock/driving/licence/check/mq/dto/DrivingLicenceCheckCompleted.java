/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.mq.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.monaco.mq.messages.AbstractMessage;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;

import java.util.List;
import java.util.UUID;

public class DrivingLicenceCheckCompleted extends AbstractMessage {
    @JsonProperty("data")
    private Data data;

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

        @JsonProperty("licence")
        private DrivingLicence licence;

        @JsonProperty("convictions")
        private List<Conviction> convictions;

        @JsonProperty("event_source")
        private String eventSource;

        public Driver getDriver() {
            return driver;
        }

        public void setDriver(Driver driver) {
            this.driver = driver;
        }

        public DrivingLicence getLicence() {
            return licence;
        }

        public void setLicence(DrivingLicence licence) {
            this.licence = licence;
        }

        public List<Conviction> getConvictions() {
            return convictions;
        }

        public void setConvictions(List<Conviction> convictions) {
            this.convictions = convictions;
        }

        public String getEventSource() {
            return eventSource;
        }

        public void setEventSource(String eventSource) {
            this.eventSource = eventSource;
        }
    }
}
