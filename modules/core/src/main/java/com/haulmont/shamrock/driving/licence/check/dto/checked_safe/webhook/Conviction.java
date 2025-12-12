/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haulmont.shamrock.driving.licence.check.joda.CheckedSafeLocalDateAdapter;
import org.joda.time.LocalDate;

public class Conviction {

    @JsonProperty("points")
    private Integer points;
    @JsonProperty("code")
    private String code;
    @JsonProperty("codeDescription")
    private String codeDescription;
    @JsonProperty("offenceDate")
    @JsonSerialize(using = CheckedSafeLocalDateAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeLocalDateAdapter.Deserializer.class)
    private LocalDate offenceDate;
    @JsonProperty("convictionDate")
    @JsonSerialize(using = CheckedSafeLocalDateAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeLocalDateAdapter.Deserializer.class)
    private LocalDate convictionDate;

    public Integer getPoints() {
        return points;
    }

    public String getCode() {
        return code;
    }

    public String getCodeDescription() {
        return codeDescription;
    }

    public LocalDate getOffenceDate() {
        return offenceDate;
    }

    public LocalDate getConvictionDate() {
        return convictionDate;
    }
}
