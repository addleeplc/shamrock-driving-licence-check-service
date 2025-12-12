/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EventType {

    @JsonProperty("LCPermissionMandateCompleted")
    PERMISSION_MANDATE_COMPLETE,
    @JsonProperty("LCCheckCompleted")
    CHECK_COMPLETE,
    @JsonProperty("LCPermissionMandateExpiring")
    PERMISSION_MANDATE_EXPIRING

}
