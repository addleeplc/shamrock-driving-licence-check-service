/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenRequest {

    @JsonProperty("username")
    private final String username;
    @JsonProperty("password")
    private final String password;
    @JsonProperty("checkEventsUrl")
    private final String checkEventsUrl;
    @JsonProperty("checkEvents")
    private final String checkEvents = "LCCheckCompleted";

    public TokenRequest(String username, String password, String checkEventsUrl) {
        this.username = username;
        this.password = password;
        this.checkEventsUrl = checkEventsUrl;
    }

}
