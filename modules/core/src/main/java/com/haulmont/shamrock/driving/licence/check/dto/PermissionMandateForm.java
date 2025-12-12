/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haulmont.bali.jackson.joda.DateTimeAdapter;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.MandateFormStatus;
import org.joda.time.DateTime;

public class PermissionMandateForm {

    @JsonProperty("signed_pdf_link")
    private final String pdf;
    @JsonProperty("sign_date")
    @JsonDeserialize(using = DateTimeAdapter.Deserializer.class)
    @JsonSerialize(using = DateTimeAdapter.Serializer.class)
    private final DateTime signDate;
    @JsonProperty("expiry_date")
    @JsonDeserialize(using = DateTimeAdapter.Deserializer.class)
    @JsonSerialize(using = DateTimeAdapter.Serializer.class)
    private final DateTime expirationDate;
    @JsonProperty("status")
    private final MandateFormStatus status;

    public PermissionMandateForm(String pdf, DateTime signDate, DateTime expirationDate, MandateFormStatus status) {
        this.pdf = pdf;
        this.signDate = signDate;
        this.expirationDate = expirationDate;
        this.status = status;
    }

    public PermissionMandateForm(MandateFormStatus status) {
        this.pdf = null;
        this.signDate = null;
        this.expirationDate = null;
        this.status = status;
    }

    public String getPdf() {
        return pdf;
    }

    public DateTime getSignDate() {
        return signDate;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    public MandateFormStatus getStatus() {
        return status;
    }
}
