/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command.chacked_safe;

import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response.CheckedSafeResponse;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.UpdateUserStatusRequest;
import kong.unirest.HttpRequest;

public class UpdateUserStatusCommand extends CheckedSafeCommand<CheckedSafeResponse> {

    private final UpdateUserStatusRequest request;

    public UpdateUserStatusCommand(UpdateUserStatusRequest request) {
        super(CheckedSafeResponse.class);
        this.request = request;
    }

    @Override
    protected HttpRequest<?> createRequest(String url, Path path) {
        return post(url, path)
                .header("Content-Type", "application/json")
                .header(TOKEN_HEADER, token)
                .body(request);
    }

    @Override
    protected Path getPath() {
        return new Path("/api/lc/updateUserStatus");
    }
}
