/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.mq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.monaco.mq.messages.AbstractMessage;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;

import java.util.List;
import java.util.UUID;

public class DrivingLicenceCheckFailed extends AbstractMessage {
    @JsonProperty("data")
    private DrivingLicenceCheckFailed.Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static final class Data {
        @JsonProperty("driver")
        private Driver driver;

        @JsonProperty("errors")
        private List<Error> errors;

        @JsonProperty("event_source")
        private String eventSource;

        public Driver getDriver() {
            return driver;
        }

        public void setDriver(Driver driver) {
            this.driver = driver;
        }

        public String getEventSource() {
            return eventSource;
        }

        public void setEventSource(String eventSource) {
            this.eventSource = eventSource;
        }

        public List<Error> getErrors() {
            return errors;
        }

        public void setErrors(List<Error> errors) {
            this.errors = errors;
        }
    }

    public static final class Error {
        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
