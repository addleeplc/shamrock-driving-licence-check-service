/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook.LicenceCheck;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response.CheckedSafeResponse;

public class WebhookData extends CheckedSafeResponse {

    @JsonProperty("event")
    private EventType event;
    @JsonProperty("licenceCheck")
    private LicenceCheck licenceCheck;

    public EventType getEvent() {
        return event;
    }

    public LicenceCheck getLicenceCheck() {
        return licenceCheck;
    }

}
