/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ClientUserIdRequest {

    @JsonProperty("clientUserId")
    public final String clientUserId;

    public ClientUserIdRequest(String clientUserId) {
        this.clientUserId = clientUserId;
    }

}
