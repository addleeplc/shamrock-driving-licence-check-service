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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LicenceCheck {

    @JsonProperty("licenceStatus")
    private String licenceStatus;
    @JsonProperty("numberOfPoints")
    private Integer numberOfPoints;
    @JsonProperty("clientUserId")
    private String clientUserId;
    @JsonProperty("riskScore")
    private RiskScore riskScore;
    @JsonProperty("riskScoreReasons")
    private List<String> riskScoreReasons;
    @JsonProperty("licenceCheckPdf")
    private String licenceCheckPdf;
    @JsonProperty("licenceValidFrom")
    @JsonSerialize(using = CheckedSafeLocalDateAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeLocalDateAdapter.Deserializer.class)
    private LocalDate licenceValidFrom;
    @JsonProperty("licenceValidTo")
    @JsonSerialize(using = CheckedSafeLocalDateAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeLocalDateAdapter.Deserializer.class)
    private LocalDate licenceValidTo;
    @JsonProperty("convictions")
    private List<Conviction> convictions = new ArrayList<>();

    public String getLicenceStatus() {
        return licenceStatus;
    }

    public Integer getNumberOfPoints() {
        return numberOfPoints;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public RiskScore getRiskScore() {
        return riskScore;
    }

    public List<String> getRiskScoreReasons() {
        return riskScoreReasons;
    }

    public String getLicenceCheckPdf() {
        return licenceCheckPdf;
    }

    public LocalDate getLicenceValidFrom() {
        return licenceValidFrom;
    }

    public LocalDate getLicenceValidTo() {
        return licenceValidTo;
    }

    public List<Conviction> getConvictions() {
        return convictions;
    }

    public enum RiskScore {
        HIGH,
        MEDIUM,
        LOW
    }
}
