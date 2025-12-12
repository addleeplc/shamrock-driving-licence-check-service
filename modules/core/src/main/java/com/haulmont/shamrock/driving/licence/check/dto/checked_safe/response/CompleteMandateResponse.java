/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.LicencePermissionMandate;

public class CompleteMandateResponse extends CheckedSafeResponse {

    @JsonProperty("licencePermissionMandate")
    private LicencePermissionMandate licencePermissionMandateId;

    public LicencePermissionMandate getLicencePermissionMandateId() {
        return licencePermissionMandateId;
    }

}
