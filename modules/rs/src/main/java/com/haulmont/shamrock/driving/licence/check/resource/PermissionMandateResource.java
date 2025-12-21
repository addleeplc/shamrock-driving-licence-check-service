package com.haulmont.shamrock.driving.licence.check.resource;

import com.haulmont.monaco.response.Response;
import com.haulmont.monaco.rs.utils.ParamUtils;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;
import com.haulmont.shamrock.driving.licence.check.dto.request.GeneratePermissionMandateFormRequest;
import com.haulmont.shamrock.driving.licence.check.dto.response.GeneratePermissionMandateFormResponse;
import com.haulmont.shamrock.driving.licence.check.dto.response.PermissionMandateFormResponse;
import com.haulmont.shamrock.driving.licence.check.dto.response.PermissionMandateFormStatusResponse;
import com.haulmont.shamrock.driving.licence.check.service.DriverRegistryService;
import com.haulmont.shamrock.driving.licence.check.LicenceCheckService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("v1/checkedsafe/permission-mandate-form")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PermissionMandateResource {

    @Inject
    private LicenceCheckService licenceCheckService;

    @Inject
    private DriverRegistryService driverRegistryService;

    @POST
    @Path("{driverId:" + ParamUtils.UUID_PATTERN_STRING + "}")
    public Response generatePermissionMandateForm(GeneratePermissionMandateFormRequest request, @PathParam("driverId") String driverIdStr) {
        Driver driver = request.getDriver();
        UUID driverId = UUID.fromString(driverIdStr);

        if (driver == null
                || driver.getDateOfBirth() == null
                || driver.getDrivingLicence() == null
                || driver.getHomeAddress() == null
                || driver.getFullName() == null) {
            driver = driverRegistryService.loadDriver(driverId);
        }

        return new GeneratePermissionMandateFormResponse(licenceCheckService.requestMandateForm(
                driverId,
                driver,
                request.getCheckSettings()
        ));
    }

    @GET
    @Path("{driverId:" + ParamUtils.UUID_PATTERN_STRING + "}")
    public PermissionMandateFormResponse getPermissionMandateForm(@PathParam("driverId") String driverId) {
        return new PermissionMandateFormResponse(licenceCheckService.getCompleteMandate(UUID.fromString(driverId)));
    }

    @GET
    @Path("{driverId:" + ParamUtils.UUID_PATTERN_STRING + "}/status")
    public PermissionMandateFormStatusResponse getPermissionMandateFormStatus(@PathParam("driverId") String driverId) {
        return new PermissionMandateFormStatusResponse(licenceCheckService.getMandateFormStatus(UUID.fromString(driverId)));
    }

}
