/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.request.checked_safe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.shamrock.driving.licence.check.dto.EventType;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.CheckedSafeError;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.StatusCode;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook.LicenceCheck;

import java.util.UUID;

public class CheckedSafeWebhookEventRequest {
    @JsonProperty("clientUserId")
    private UUID clientUserId;

    @JsonProperty("event")
    private EventType eventType;

    @JsonProperty("statusCode")
    private StatusCode statusCode;

    @JsonProperty("error")
    private CheckedSafeError checkedSafeError;

    @JsonProperty("licenceCheck")
    private LicenceCheck licenceCheck;

    public LicenceCheck getLicenceCheck() {
        return licenceCheck;
    }

    public void setLicenceCheck(LicenceCheck licenceCheck) {
        this.licenceCheck = licenceCheck;
    }

    public StatusCode getStatus() {
        return statusCode;
    }

    public CheckedSafeError getError() {
        return checkedSafeError;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public StatusCode getResponseStatus() {
        return statusCode;
    }

    public void setResponseStatus(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public UUID getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(UUID clientUserId) {
        this.clientUserId = clientUserId;
    }
}
