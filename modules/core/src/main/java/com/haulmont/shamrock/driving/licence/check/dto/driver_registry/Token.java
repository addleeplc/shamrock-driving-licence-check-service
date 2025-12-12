/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.driver_registry;

import org.joda.time.DateTime;

public class Token {

    private final String token;
    private final DateTime validUntil;

    public Token(String token, DateTime validUntil) {
        this.token = token;
        this.validUntil = validUntil;
    }

    public String getToken() {
        return token;
    }

    public DateTime getValidUntil() {
        return validUntil;
    }
}
