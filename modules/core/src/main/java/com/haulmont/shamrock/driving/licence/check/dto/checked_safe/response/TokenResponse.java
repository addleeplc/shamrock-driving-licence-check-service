/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.ApiToken;

public class TokenResponse extends CheckedSafeResponse {

    @JsonProperty("apiToken")
    private ApiToken apiToken;

    public ApiToken getApiToken() {
        return apiToken;
    }

}
