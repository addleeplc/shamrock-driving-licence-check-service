/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MandateFormResponse extends CheckedSafeResponse {

    @JsonProperty("permissionMandateRequest")
    private PermissionMandateRequest permissionMandateRequest;

    public PermissionMandateRequest getPermissionMandateRequest() {
        return permissionMandateRequest;
    }

    public static class PermissionMandateRequest {
        @JsonProperty("permissionMandateUrl")
        private String permissionMandateUrl;

        public String getPermissionMandateUrl() {
            return permissionMandateUrl;
        }

    }

}
