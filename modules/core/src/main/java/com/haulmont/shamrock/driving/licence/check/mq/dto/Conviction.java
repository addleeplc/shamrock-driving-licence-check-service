/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.mq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haulmont.bali.jackson.joda.DateTimeAdapter;
import org.joda.time.DateTime;

import java.math.BigDecimal;

public class Conviction {
    @JsonProperty("offence_code")
    private String code;

    @JsonProperty("note")
    private String description;

    @JsonProperty("points")
    private int points;

    @JsonProperty("date")
    @JsonSerialize(using = DateTimeAdapter.Serializer.class)
    @JsonDeserialize(using = DateTimeAdapter.Deserializer.class)
    private DateTime convictionDate;

    @JsonProperty("offence_date")
    @JsonSerialize(using = DateTimeAdapter.Serializer.class)
    @JsonDeserialize(using = DateTimeAdapter.Deserializer.class)
    private DateTime offenceDate;

    @JsonProperty("fine")
    private BigDecimal fine;

    @JsonProperty("suspension_from")
    @JsonSerialize(using = DateTimeAdapter.Serializer.class)
    @JsonDeserialize(using = DateTimeAdapter.Deserializer.class)
    private DateTime suspensionFrom;

    @JsonProperty("suspension_till")
    @JsonSerialize(using = DateTimeAdapter.Serializer.class)
    @JsonDeserialize(using = DateTimeAdapter.Deserializer.class)
    private DateTime suspensionTill;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public DateTime getConvictionDate() {
        return convictionDate;
    }

    public void setConvictionDate(DateTime convictionDate) {
        this.convictionDate = convictionDate;
    }

    public DateTime getOffenceDate() {
        return offenceDate;
    }

    public void setOffenceDate(DateTime offenceDate) {
        this.offenceDate = offenceDate;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public DateTime getSuspensionFrom() {
        return suspensionFrom;
    }

    public void setSuspensionFrom(DateTime suspensionFrom) {
        this.suspensionFrom = suspensionFrom;
    }

    public DateTime getSuspensionTill() {
        return suspensionTill;
    }

    public void setSuspensionTill(DateTime suspensionTill) {
        this.suspensionTill = suspensionTill;
    }
}
