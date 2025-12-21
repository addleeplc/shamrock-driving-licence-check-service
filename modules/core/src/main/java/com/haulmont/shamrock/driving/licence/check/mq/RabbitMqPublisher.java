/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.mq;

import com.haulmont.monaco.AppContext;
import com.haulmont.monaco.ServiceException;
import com.haulmont.monaco.mq.messages.AbstractMessage;
import com.haulmont.monaco.rabbit.mq.RabbitMqClient;
import com.haulmont.monaco.response.ErrorCode;
import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;

import java.io.IOException;

@Component
public class RabbitMqPublisher {


    @Inject
    private ServiceConfiguration configuration;

    public <T extends AbstractMessage> void publish(T event) {
        try {
            getMqClient().publish(configuration.getLicenceCheckExchange(), event.getClass().getSimpleName(), event);
        } catch (IOException e) {
            throw new ServiceException(ErrorCode.SERVER_ERROR, String.format("Fail to send message to RMQ due to error (message.id: %s, message.class: %s)", event.getId(), event.getClass().getSimpleName()), e);
        }
    }

    private RabbitMqClient getMqClient() {
        return AppContext.getResources().get(configuration.getMqResource(), RabbitMqClient.class);
    }
}
