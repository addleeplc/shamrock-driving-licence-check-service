/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.monaco.response.ErrorCode;
import com.haulmont.monaco.response.Response;
import com.haulmont.shamrock.driving.licence.check.dto.UrlWrapper;

public class GeneratePermissionMandateFormResponse extends Response {

    @JsonProperty("permission_mandate_form")
    private final UrlWrapper urlWrapper;

    public GeneratePermissionMandateFormResponse(String url) {
        super(ErrorCode.OK);
        this.urlWrapper = new UrlWrapper(url);
    }

}
