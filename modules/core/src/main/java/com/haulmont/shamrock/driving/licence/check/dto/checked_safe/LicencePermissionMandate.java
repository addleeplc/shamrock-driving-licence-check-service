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

public class LicencePermissionMandate {

    @JsonProperty("clientUserId")
    private String clientUserId;
    @JsonProperty("permissionMandatePdf")
    private String permissionMandatePdf;
    @JsonProperty("completedOn")
    @JsonSerialize(using = CheckedSafeDateTimeAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeDateTimeAdapter.Deserializer.class)
    private DateTime completedOn;
    @JsonProperty("expiresOn")
    @JsonSerialize(using = CheckedSafeDateTimeAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeDateTimeAdapter.Deserializer.class)
    private DateTime expiresOn;

    public String getClientUserId() {
        return clientUserId;
    }

    public String getPermissionMandatePdf() {
        return permissionMandatePdf;
    }

    public DateTime getCompletedOn() {
        return completedOn;
    }

    public DateTime getExpiresOn() {
        return expiresOn;
    }

}
