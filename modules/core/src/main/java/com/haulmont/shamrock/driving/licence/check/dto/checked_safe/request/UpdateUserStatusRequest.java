/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.ClientStatus;

import java.util.UUID;

public class UpdateUserStatusRequest {

    @JsonProperty("clientUserId")
    private final String clientUserId;
    @JsonProperty("newStatus")
    private final ClientStatus newStatus;


    public UpdateUserStatusRequest(String clientUserId, ClientStatus newStatus) {
        this.clientUserId = clientUserId;
        this.newStatus = newStatus;
    }

}
