/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haulmont.shamrock.driving.licence.check.joda.CheckedSafeLocalDateAdapter;
import com.haulmont.shamrock.driving.licence.check.joda.CheckedSafeLocalDateReverseAdapter;
import org.joda.time.LocalDate;

import java.util.UUID;

public class MandateFormRequest {

    @JsonProperty("DLN")
    private final String dln;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("dateOfBirth")
    @JsonSerialize(using = CheckedSafeLocalDateReverseAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeLocalDateReverseAdapter.Deserializer.class)
    private final LocalDate dateOfBirth;
    @JsonProperty("address")
    private final String address;
    @JsonProperty("postcode")
    private final String postcode;
    @JsonProperty("licenceValidTo")
    @JsonSerialize(using = CheckedSafeLocalDateAdapter.Serializer.class)
    @JsonDeserialize(using = CheckedSafeLocalDateAdapter.Deserializer.class)
    private final LocalDate licenceValidTo;
    @JsonProperty("includeCpc")
    private final Boolean includeCpc;
    @JsonProperty("includeTacho")
    private final Boolean includeTacho;
    @JsonProperty("clientUserId")
    private final String clientUserId;
    @JsonProperty("linkExpiryDuration")
    private final Integer linkExpiryDuration;
    @JsonProperty("successRedirectUrl")
    private final String successRedirectUrl;

    public MandateFormRequest(String dln, String name, LocalDate dateOfBirth, String address, String postcode, LocalDate licenceValidTo, Boolean includeCpc, Boolean includeTacho, String clientUserId, Integer linkExpiryDuration, String successRedirectUrl) {
        this.dln = dln;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.postcode = postcode;
        this.licenceValidTo = licenceValidTo;
        this.includeCpc = includeCpc;
        this.includeTacho = includeTacho;
        this.clientUserId = clientUserId;
        this.linkExpiryDuration = linkExpiryDuration;
        this.successRedirectUrl = successRedirectUrl;
    }

}
