/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command.chacked_safe;

import com.haulmont.monaco.unirest.UnirestCommand;
import org.apache.http.HttpStatus;

public abstract class CheckedSafeCommand<T>  extends UnirestCommand<T> {

    private static final String SERVICE = "checked-safe";
    protected static final String TOKEN_HEADER = "x-cs-token";
    public static final String CALLBACK_TOKEN_HEADER = "x-callback-token";

    protected String token;

    public CheckedSafeCommand(Class<T> responseClass) {
        super(SERVICE, responseClass);
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected boolean isOK(int status) {
        return super.isOK(status)
                || status == HttpStatus.SC_BAD_REQUEST
                || status == HttpStatus.SC_NOT_FOUND;
    }
}
