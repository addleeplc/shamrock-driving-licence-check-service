/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LicenceStatus {
    @JsonProperty("Valid Full Licence")
    VALID,
    @JsonProperty("Disqualified Full Licence")
    DISQUALIFIED,
    @JsonProperty("Expired Full Licence")
    EXPIRED

}
