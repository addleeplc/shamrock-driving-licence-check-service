/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlWrapper {

    @JsonProperty("signing_link")
    private final String url;

    public UrlWrapper(String url) {
        this.url = url;
    }

}
