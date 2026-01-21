/*
 * Copyright 2008 - 2022 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.mq;

import com.haulmont.monaco.mq.annotations.Subscribe;
import com.haulmont.monaco.rabbit.mq.annotations.Consumer;
import com.haulmont.shamrock.driving.licence.check.ConfigurationService;
import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.ClientStatus;
import com.haulmont.shamrock.driving.licence.check.LicenceCheckService;
import com.haulmont.shamrock.driving.licence.check.service.DriverRegistryService;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;

@Component
@Consumer(server = ServiceConfiguration.MQ_DRIVER_CHANGE_RESOURCE_NAME, queue = ServiceConfiguration.MQ_DRIVER_CHANGE_CONSUMER)
public class DriverChangeConsumer {

    @Inject
    private ConfigurationService configurationService;
    @Inject
    private Logger log;
    @Inject
    private LicenceCheckService licenceCheckService;
    @Inject
    private DriverRegistryService driverRegistryService;

    @Subscribe
    public void handleDriverUpdated(DriverUpdated driverUpdated){
        try {
            if (driverUpdated.getData() == null || driverUpdated.getData().getDriver() == null) {
                log.debug("ignore {} update due empty data or driver", driverUpdated.getId());
                return;
            }

            var driver = driverUpdated.getData().getDriver();
            if (driver.getStatus() == null) {
                driver = driverRegistryService.loadDriver(driver.getId());
            }

            if (configurationService.getInactiveStatuses().contains(driver.getStatus().getCode())) {
                licenceCheckService.updateClientStatus(driver.getNumber(), ClientStatus.INACTIVE);
            } else {
                licenceCheckService.updateClientStatus(driver.getNumber(), ClientStatus.ACTIVE);
            }
        } catch (Throwable t) {
            log.error("Error while processing DriverUpdated", t);
        }
    }


}
