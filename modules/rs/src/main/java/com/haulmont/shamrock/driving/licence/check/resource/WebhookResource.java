/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.resource;

import com.haulmont.monaco.response.ErrorCode;
import com.haulmont.monaco.response.Response;
import com.haulmont.shamrock.driving.licence.check.LicenceCheckService;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.request.TokenRequest;
import com.haulmont.shamrock.driving.licence.check.dto.request.checked_safe.CheckedSafeWebhookEventRequest;
import com.haulmont.shamrock.driving.licence.check.DrivingLicenceRepository;
import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

import static com.haulmont.shamrock.driving.licence.check.dto.EventType.CHECK_COMPLETE;
import static com.haulmont.shamrock.driving.licence.check.dto.checked_safe.StatusCode.SUCCESS;
import static com.haulmont.shamrock.driving.licence.check.utils.EventConverter.convert;

@Path("v1/checkedsafe/webhooks/event")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class WebhookResource {

    private static final Logger log = LoggerFactory.getLogger(WebhookResource.class);
    @Inject
    private ServiceConfiguration serviceConfiguration;
    @Inject
    private DrivingLicenceRepository driverProfileService;
    @Inject
    private EmailService emailService;
    @Inject
    private LicenceCheckService licenceCheckService;

    /**
     * @see TokenRequest#checkEvents
     */
    @POST
    public Response handleWebhook(CheckedSafeWebhookEventRequest request) {
        if (request.getEventType().equals(CHECK_COMPLETE)) {
            if (request.getStatus().equals(SUCCESS)) {
                log.info("Received successful LicenceCheckCompleted request for Driver({})", request.getLicenceCheck().getClientUserId());

                licenceCheckService.handleLicenceCheck(request.getLicenceCheck());
            } else {
                log.info("Received failed LicenceCheckCompleted request: {}", request.getError().getMessage());
                //todo driverId, awaiting response from checkedsafe
                licenceCheckService.handleLicenceCheckError(request.getDriverId(), request.getError());
            }

            return new Response(ErrorCode.OK);
        } else {
            log.info("Received request type: {} is not supported", request.getEventType());

            return new Response(ErrorCode.BAD_REQUEST.getCode(), "Request type is not supported");
        }
    }
}
