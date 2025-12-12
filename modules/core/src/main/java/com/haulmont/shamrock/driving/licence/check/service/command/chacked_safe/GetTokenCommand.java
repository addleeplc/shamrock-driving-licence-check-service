/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command.chacked_safe;

import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.TokenRequest;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.response.TokenResponse;
import kong.unirest.HttpRequest;

public class GetTokenCommand extends CheckedSafeCommand<TokenResponse> {

    private final String callbackToken;
    private final TokenRequest tokenRequest;

    public GetTokenCommand(String callbackToken, TokenRequest tokenRequest) {
        super(TokenResponse.class, null);
        this.callbackToken = callbackToken;
        this.tokenRequest = tokenRequest;
    }

    @Override
    protected HttpRequest<?> createRequest(String url, Path path) {
        return post(url, path)
                .header("Content-Type", "application/json")
                .header(CALLBACK_TOKEN_HEADER, callbackToken)
                .body(tokenRequest);
    }

    @Override
    protected Path getPath() {
        return new Path("/api/token/request");
    }
}
