/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.mq.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haulmont.bali.jackson.joda.LocalDateAdapter;
import com.haulmont.monaco.jackson.annotations.SensitiveData;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

public class DrivingLicence {
    @JsonProperty("number")
    @SensitiveData
    private String number;

    @JsonSerialize(using = LocalDateAdapter.Serializer.class)
    @JsonDeserialize(using = LocalDateAdapter.Deserializer.class)
    @JsonProperty("expiry_date")
    private LocalDate expiryDate;

    @JsonSerialize(using = LocalDateAdapter.Serializer.class)
    @JsonDeserialize(using = LocalDateAdapter.Deserializer.class)
    @JsonProperty("inspection_date")
    private LocalDate inspectionDate;

    @JsonProperty("points")
    private int points;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("type")
    private Type type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Status {
        VALID("valid"),
        DISQUALIFIED("disqualified"),
        REVOKED("revoked"),
        SURRENDERED("surrendered"),
        EXPIRED("expired"),
        EXCHANGED("exchanged"),
        REFUSED("refused");

        private static final Map<String, Status> constants = new HashMap<String, Status>();
        static {
            for (Status c : values()) {
                constants.put(c.code, c);
            }
        }

        private final String code;

        Status(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.code;
        }

        @JsonCreator
        public static Status fromValue(String value) {
            Status constant = constants.get(value.toLowerCase());
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }

    public enum Type {
        FULL("full"),
        PROVISIONAL("provisional");

        private static final Map<String, Type> constants = new HashMap<>();
        static {
            for (Type c : values()) {
                constants.put(c.code, c);
            }
        }

        private final String code;

        Type(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.code;
        }

        public static Type fromValue(String value) {
            Type constant = constants.get(value.toLowerCase());
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}
