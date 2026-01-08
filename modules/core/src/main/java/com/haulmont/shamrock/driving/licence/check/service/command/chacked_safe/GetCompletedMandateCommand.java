/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command.chacked_safe;

import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.ClientUserIdRequest;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response.CompletedMandateResponse;
import kong.unirest.HttpRequest;

import java.util.UUID;

public class GetCompletedMandateCommand extends CheckedSafeCommand<CompletedMandateResponse> {

    private final UUID clientUserId;

    public GetCompletedMandateCommand(UUID clientUserId) {
        super(CompletedMandateResponse.class);
        this.clientUserId = clientUserId;
    }

    @Override
    protected HttpRequest<?> createRequest(String url, Path path) {
        return post(url, path)
                .header("Content-Type", "application/json")
                .header(TOKEN_HEADER, token)
                .body(new ClientUserIdRequest(clientUserId));
    }

    @Override
    protected Path getPath() {
        return new Path("/api/lc/getCompletedMandate");
    }
}
