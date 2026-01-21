/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command.chacked_safe;

import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.ClientUserIdRequest;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response.PermissionMandateFormStatusResponse;
import kong.unirest.HttpRequest;

public class RequestPermissionMandateFormStatusCommand extends CheckedSafeCommand<PermissionMandateFormStatusResponse> {

    private final String driverNumber;

    public RequestPermissionMandateFormStatusCommand(String driverNumber) {
        super(PermissionMandateFormStatusResponse.class);
        this.driverNumber = driverNumber;
    }

    @Override
    protected HttpRequest<?> createRequest(String url, Path path) {
        return post(url, path)
                .header("Content-Type", "application/json")
                .header(TOKEN_HEADER, token)
                .body(new ClientUserIdRequest(driverNumber));
    }

    @Override
    protected Path getPath() {
        return new Path("/api/lc/requestPermissionMandateFormStatus");
    }
}
