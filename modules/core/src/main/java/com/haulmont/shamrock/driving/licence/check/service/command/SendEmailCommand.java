/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command;

import com.haulmont.monaco.response.Response;
import com.haulmont.monaco.unirest.UnirestCommand;
import com.haulmont.shamrock.driving.licence.check.dto.email.Message;
import kong.unirest.HttpRequest;

public class SendEmailCommand extends UnirestCommand<Response> {

    private static final String SERVICE = "shamrock-email-service";

    private final Message message;

    public SendEmailCommand(Message message) {
        super(SERVICE, Response.class);
        this.message = message;
    }

    @Override
    protected HttpRequest<?> createRequest(String url, Path path) {
        return post(url, path)
                .header("Content-Type", "application/json")
                .body(message);
    }

    @Override
    protected Path getPath() {
        return new Path("emails");
    }
}
