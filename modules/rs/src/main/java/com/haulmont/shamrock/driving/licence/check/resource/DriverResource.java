/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.resource;

import com.haulmont.monaco.response.ErrorCode;
import com.haulmont.monaco.response.Response;
import com.haulmont.monaco.rs.utils.ParamUtils;
import com.haulmont.shamrock.driving.licence.check.service.LicenceCheckService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("v1/drivers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class DriverResource {

    @Inject
    private LicenceCheckService licenceCheckService;

    @POST
    @Path("{driverId:" + ParamUtils.UUID_PATTERN_STRING + "}/check")
    public Response triggerLicenceCheck(@PathParam("driverId") String driverId) {
        licenceCheckService.triggerAdHocCheck(UUID.fromString(driverId));
        return new Response(ErrorCode.OK);
    }

}
