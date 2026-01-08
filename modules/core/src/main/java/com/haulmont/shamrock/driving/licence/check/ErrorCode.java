/*
 * Copyright 2008 - 2026 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check;

public class ErrorCode extends com.haulmont.monaco.response.ErrorCode {
    public static ErrorCode TOKEN_LOCK_ERROR = new ErrorCode(1, "Token lock acquired by other process");

    public ErrorCode(int code, String message) {
        super(code, message);
    }
}
