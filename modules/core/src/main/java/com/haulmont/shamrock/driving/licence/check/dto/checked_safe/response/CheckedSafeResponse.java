/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.CheckedSafeError;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.StatusCode;

public class CheckedSafeResponse {

    @JsonProperty("statusCode")
    private StatusCode statusCode;
    @JsonProperty("error")
    private CheckedSafeError checkedSafeError;

    public StatusCode getStatus() {
        return statusCode;
    }

    public CheckedSafeError getError() {
        return checkedSafeError;
    }

}
