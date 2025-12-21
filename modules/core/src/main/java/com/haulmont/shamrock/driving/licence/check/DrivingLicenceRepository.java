/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check;

import com.haulmont.shamrock.driving.licence.check.mq.RabbitMqPublisher;
import com.haulmont.shamrock.driving.licence.check.mq.dto.DrivingLicenceCheckCompleted;
import com.haulmont.shamrock.driving.licence.check.mybatis.SqlSessionFactory;
import com.haulmont.shamrock.driving.licence.check.service.DriverRegistryService;
import com.haulmont.shamrock.driving.licence.check.service.EmailService;
import com.haulmont.shamrock.driving.licence.check.service.command.PersistCheckResultCommand;
import org.joda.time.DateTime;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;

import java.util.Collections;


@Component
public class DrivingLicenceRepository {

    @Inject
    private SqlSessionFactory sqlSessionFactory;
    @Inject
    private DriverRegistryService driverRegistryService;
    @Inject
    private EmailService emailService;
    @Inject
    private ServiceConfiguration serviceConfiguration;
    @Inject
    private RabbitMqPublisher rabbitMqPublisher;

    public void persistCheckResult(DrivingLicenceCheckCompleted licenceCheckCompleted) {
        DrivingLicenceCheckCompleted.Data data = licenceCheckCompleted.getData();

        new PersistCheckResultCommand(
                sqlSessionFactory,
                serviceConfiguration.getShamrockUserId(),
                data.getDriver().getPid(),
                data.getLicence().getPoints(),
                DateTime.now(),
//                data.getConvictions() //todo what to do with convictions?
                Collections.emptyList()
        ).execute();


//        Driver driver = driverRegistryService.loadDriver(licenceCheck.getClientUserId());
//        if (licenceCheck.getLicenceStatus().equals(EXPIRED)) {
//            driverRegistryService.updateWorkingStatus(
//                    licenceCheck.getClientUserId(),
//                    workingStatus(
//                            serviceConfiguration.getLicenceCheckIncorrectWorkingStatus(),
//                            serviceConfiguration.getLicenceCheckIncorrectWorkingStatusReason())
//            );
//            emailService.sendIncorrectCheckMail(String.format("Driver '%s' (call sign) has not updated their driving licence which has now expired.", driver.getCallsign()));
//            return;
//        } else if (licenceCheck.getLicenceStatus().equals(DISQUALIFIED)) {
//            driverRegistryService.updateWorkingStatus(
//                    licenceCheck.getClientUserId(),
//                    workingStatus(
//                            serviceConfiguration.getLicenceCheckIncorrectWorkingStatus(),
//                            serviceConfiguration.getLicenceCheckIncorrectWorkingStatusReason())
//            );
//            emailService.sendDeclinedCheckMail(String.format("Driver '%s' (call sign) has licence which has now disqualified.", driver.getCallsign()));
//            return;
//        }

//        new UpdateDriverProfileCommand(
//                sqlSessionFactory,
//                serviceConfiguration.getShamrockUserId(),
//                licenceCheck.getClientUserId(),
//                licenceCheck.getNumberOfPoints(),
//                DateTime.now(),
//                licenceCheck.getConvictions()
//        ).execute();



//        driverRegistryService.updateWorkingStatus(
//                licenceCheck.getClientUserId(),
//                workingStatus(
//                        serviceConfiguration.getLicenceCheckApprovedWorkingStatus(),
//                        serviceConfiguration.getLicenceCheckApprovedWorkingStatusReason())
//        );
    }
}
