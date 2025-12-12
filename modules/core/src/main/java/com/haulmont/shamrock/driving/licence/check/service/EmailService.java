/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service;

import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.dto.email.Address;
import com.haulmont.shamrock.driving.licence.check.dto.email.JsonMail;
import com.haulmont.shamrock.driving.licence.check.dto.email.Message;
import com.haulmont.shamrock.driving.licence.check.dto.email.Part;
import com.haulmont.shamrock.driving.licence.check.service.command.SendEmailCommand;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;

import java.util.Collections;

import static com.haulmont.monaco.unirest.ServiceCallUtils.call;

@Component
public class EmailService {

    @Inject
    private ServiceConfiguration serviceConfiguration;
    @Inject
    private Logger log;

    public void sendIncorrectCheckMail(String error) {
        String msg = serviceConfiguration.getLicenceCheckIncorrectMailMessage();

        sendEmail(String.format(msg,error));
    }

    public void sendDeclinedCheckMail(String error) {
        String msg = serviceConfiguration.getLicenceCheckDeclinedMailMessage();

        sendEmail(String.format(msg,error));
    }

    private void sendEmail(String text) {
        Part part = new Part(
                text,
                "text/plain"
        );
        JsonMail email = new JsonMail(
                serviceConfiguration.getEmailSubject(),
                Collections.singletonList(new Address(serviceConfiguration.getEmailTo())),
                Collections.singletonList(part)
        );
        Message message = new Message(email);
        call(() -> new SendEmailCommand(message), response -> null);
        log.info("Sent email: {}", text);
    }

}
