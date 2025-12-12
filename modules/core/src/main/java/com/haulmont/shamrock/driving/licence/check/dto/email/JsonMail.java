/*
 * Copyright (c) 2016 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.email;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonMail {

    @JsonProperty("subject")
    private final String subject;

    @JsonProperty("to")
    private final List<Address> to;

    @JsonProperty("parts")
    private final List<Part> parts;

    @JsonProperty("encoding")
    private final String encoding = "UTF-8";

    public JsonMail(String subject, List<Address> to, List<Part> parts) {
        this.subject = subject;
        this.to = to;
        this.parts = parts;
    }
}
