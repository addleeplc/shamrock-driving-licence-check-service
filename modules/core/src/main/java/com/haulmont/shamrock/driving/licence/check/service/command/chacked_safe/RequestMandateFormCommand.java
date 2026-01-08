/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command.chacked_safe;

import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.MandateFormRequest;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response.MandateFormResponse;
import kong.unirest.HttpRequest;

public class RequestMandateFormCommand extends CheckedSafeCommand<MandateFormResponse> {

    private final MandateFormRequest request;

    public RequestMandateFormCommand(MandateFormRequest request) {
        super(MandateFormResponse.class);
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
        return new Path("/api/lc/requestMandateForm");
    }
}
