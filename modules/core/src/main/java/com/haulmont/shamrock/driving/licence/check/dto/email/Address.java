/*
 * Copyright (c) 2016 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.email;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

    @JsonProperty("email")
    private final String email;

    public Address(String email) {
        this.email = email;
    }

}
