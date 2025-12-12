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

public class CheckedSafeError {

    @JsonProperty("message")
    private String message;
    @JsonProperty("dateTime")
    @JsonSerialize(using = CheckedSafeDateTimeAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeDateTimeAdapter.Deserializer.class)
    private DateTime dateTime;

    public String getMessage() {
        return message;
    }


    public DateTime getDateTime() {
        return dateTime;
    }

}
