/*
 * Copyright (c) 2016 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.email;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Part {

    @JsonProperty("content")
    private final String content;

    @JsonProperty("content_type")
    private final String contentType;

    public Part(String content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }
}
