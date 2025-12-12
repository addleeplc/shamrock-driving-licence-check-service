/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.monaco.response.ErrorCode;
import com.haulmont.monaco.response.Response;
import com.haulmont.shamrock.driving.licence.check.dto.PermissionMandateForm;

public class PermissionMandateFormResponse extends Response {

    @JsonProperty("permission_mandate_form")
    private final PermissionMandateForm permissionMandateForm;

    public PermissionMandateFormResponse(PermissionMandateForm permissionMandateForm) {
        super(ErrorCode.OK);
        this.permissionMandateForm = permissionMandateForm;
    }

}
