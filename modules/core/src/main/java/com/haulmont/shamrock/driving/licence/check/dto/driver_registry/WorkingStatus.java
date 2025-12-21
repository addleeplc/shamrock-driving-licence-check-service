/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.driver_registry;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class WorkingStatus {
    @JsonProperty("code")
    private String code;

    @JsonProperty("reason")
    private String reason;

    public WorkingStatus() {
    }

    public WorkingStatus(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WorkingStatus that = (WorkingStatus) o;
        return Objects.equals(code, that.code) && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, reason);
    }

    public static WorkingStatus workingStatus(String code, String reason) {
        return new WorkingStatus(code, reason);
    }
}
