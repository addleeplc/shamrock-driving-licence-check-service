/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haulmont.shamrock.driving.licence.check.joda.CheckedSafeDateTimeAdapter;
import org.joda.time.DateTime;

import java.util.UUID;

public class PermissionMandateFormStatus {

    @JsonProperty("permissionMandateUrl")
    private String permissionMandateUrl;
    @JsonProperty("expired")
    private Boolean expired;
    @JsonProperty("expiresOn")
    @JsonSerialize(using = CheckedSafeDateTimeAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeDateTimeAdapter.Deserializer.class)
    private DateTime expiresOn;
    @JsonProperty("clientUserId")
    private String clientUserId;
    @JsonProperty("mandateFormCompleted")
    private Boolean mandateFormCompleted;

    public String getPermissionMandateUrl() {
        return permissionMandateUrl;
    }

    public Boolean getExpired() {
        return expired;
    }

    public DateTime getExpiresOn() {
        return expiresOn;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public Boolean getMandateFormCompleted() {
        return mandateFormCompleted;
    }

}
