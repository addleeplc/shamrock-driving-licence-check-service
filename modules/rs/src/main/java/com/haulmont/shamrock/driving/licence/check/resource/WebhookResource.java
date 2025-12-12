/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.resource;

import com.haulmont.monaco.response.ErrorCode;
import com.haulmont.monaco.response.Response;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.TokenRequest;
import com.haulmont.shamrock.driving.licence.check.service.DriverProfileService;
import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.dto.WebhookData;
import com.haulmont.shamrock.driving.licence.check.service.EmailService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

import static com.haulmont.shamrock.driving.licence.check.dto.EventType.CHECK_COMPLETE;
import static com.haulmont.shamrock.driving.licence.check.dto.checked_safe.ResponseStatus.SUCCESS;

@Path("v1/checkedsafe/webhooks/event")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class WebhookResource {

    private static final Logger log = LoggerFactory.getLogger(WebhookResource.class);
    @Inject
    private ServiceConfiguration serviceConfiguration;
    @Inject
    private DriverProfileService driverProfileService;
    @Inject
    private EmailService emailService;

    /**
     * @see TokenRequest#checkEvents
     */
    @POST
    public Response handleWebhook(@HeaderParam("x-callback-token") String callbackToken, @RequestBody WebhookData event) {
        if (!Objects.equals(callbackToken, serviceConfiguration.getCheckedSafeCallbackToken())) {
            return new Response(ErrorCode.UNAUTHORIZED.getCode(), "x-callback-token header is required");
        }

        if (event.getEvent().equals(CHECK_COMPLETE)) {
            if (event.getStatus().equals(SUCCESS)) {
                log.info("Received webhook check complete for Driver({})", event.getLicenceCheck().getClientUserId());
                driverProfileService.updateProfile(event.getLicenceCheck());
            } else {
                log.info("Received webhook check not complete: {}", event.getError().getMessage());
                emailService.sendDeclinedCheckMail(event.getError().getMessage());
            }
        } else {
            log.info("{} not supported", event.getEvent());
        }

        return new Response(ErrorCode.OK);
    }

}
