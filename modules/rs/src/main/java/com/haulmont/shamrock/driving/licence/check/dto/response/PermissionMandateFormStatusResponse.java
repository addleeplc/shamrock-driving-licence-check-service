/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.monaco.response.ErrorCode;
import com.haulmont.monaco.response.Response;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.MandateFormStatus;

public class PermissionMandateFormStatusResponse extends Response {

    @JsonProperty("status")
    private final MandateFormStatus status;

    public PermissionMandateFormStatusResponse(MandateFormStatus status) {
        super(ErrorCode.OK);
        this.status = status;
    }

}
