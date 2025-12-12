/*
 * Copyright 2008 - 2022 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.mq;

import com.haulmont.monaco.mq.annotations.Subscribe;
import com.haulmont.monaco.rabbit.mq.annotations.Consumer;
import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.ClientStatus;
import com.haulmont.shamrock.driving.licence.check.service.LicenceCheckService;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;

@Component
@Consumer(server = ServiceConfiguration.MQ_DRIVER_CHANGE_RESOURCE_NAME, queue = ServiceConfiguration.MQ_DRIVER_CHANGE_CONSUMER)
public class DriverChangeConsumer {

    @Inject
    private Logger log;
    @Inject
    private LicenceCheckService licenceCheckService;

    @Subscribe
    public void handleDriverUpdated(DriverUpdated driverUpdated){
        try {
            if (driverUpdated.getData() == null) {
                log.debug("ignore {} update due empty data", driverUpdated.getId());
                return;
            }

            var driver = driverUpdated.getData().getDriver();
            if (driver == null || driver.getStatus() == null) {
                log.debug("ignore {} update due empty working status", driverUpdated.getId());
                return;
            }

            if (driver.getStatus().getCode().equals("Inactive")) {
                licenceCheckService.updateClientStatus(driver.getId(), ClientStatus.INACTIVE);
            } else {
                licenceCheckService.updateClientStatus(driver.getId(), ClientStatus.ACTIVE);
            }
        } catch (Throwable t) {
            log.error("Error while processing DriverUpdated", t);
        }
    }


}
