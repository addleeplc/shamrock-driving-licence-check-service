/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.utils;

import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.CheckedSafeError;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook.LicenceCheck;
import com.haulmont.shamrock.driving.licence.check.dto.driver_registry.Driver;
import com.haulmont.shamrock.driving.licence.check.mq.dto.Conviction;
import com.haulmont.shamrock.driving.licence.check.mq.dto.DrivingLicence;
import com.haulmont.shamrock.driving.licence.check.mq.dto.DrivingLicence.Status;
import com.haulmont.shamrock.driving.licence.check.mq.dto.DrivingLicence.Type;
import com.haulmont.shamrock.driving.licence.check.mq.dto.DrivingLicenceCheckCompleted;
import com.haulmont.shamrock.driving.licence.check.mq.dto.DrivingLicenceCheckFailed;
import org.joda.time.DateTime;

import java.util.Objects;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class EventConverter {
    public static DrivingLicenceCheckCompleted convert(Driver driver, LicenceCheck licenceCheck) {
        DrivingLicenceCheckCompleted event = new DrivingLicenceCheckCompleted();
        DrivingLicenceCheckCompleted.Data data = new DrivingLicenceCheckCompleted.Data();

        data.setDriver(copyMinimal(driver));
        data.setEventSource("checkedsafe");

        DrivingLicence drivingLicence = new DrivingLicence();
        drivingLicence.setNumber(driver.getDrivingLicence());
        drivingLicence.setPoints(licenceCheck.getNumberOfPoints());
        drivingLicence.setExpiryDate(licenceCheck.getLicenceValidTo());
        drivingLicence.setInspectionDate(DateTime.now().toLocalDate());
        initLicenceStatusAndType(licenceCheck.getLicenceStatus(), drivingLicence);

        data.setLicence(drivingLicence);

        data.setConvictions(
                licenceCheck.getConvictions().stream()
                        .map(EventConverter::convert)
                        .filter(Objects::nonNull)
                        .collect(toList())
        );

        event.setData(data);

        return event;
    }

    public static DrivingLicenceCheckFailed convert(Driver driver, CheckedSafeError error) {
        DrivingLicenceCheckFailed event = new DrivingLicenceCheckFailed();
        DrivingLicenceCheckFailed.Data data = new DrivingLicenceCheckFailed.Data();

        data.setDriver(copyMinimal(driver));
        data.setErrors(singletonList(convertError(error)));
        event.setData(data);

        return event;
    }

    public static Conviction convert(com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook.Conviction origin) {
        Conviction conviction =  new Conviction();
//
        if (origin.getConvictionDate() == null || origin.getCode() == null) {
            return null;
        }

        conviction.setConvictionDate(origin.getConvictionDate().toDateTimeAtStartOfDay());
        conviction.setOffenceDate(origin.getOffenceDate().toDateTimeAtStartOfDay());
        conviction.setCode(origin.getCode());
        conviction.setPoints(origin.getPoints());
        conviction.setDescription(origin.getCodeDescription());

        return conviction;
    }

    public static DrivingLicenceCheckFailed.Error convertError(CheckedSafeError origin) {
        DrivingLicenceCheckFailed.Error error = new DrivingLicenceCheckFailed.Error();

        error.setCode("error");
        error.setMessage(origin.getMessage());

        return error;
    }

    private static void initLicenceStatusAndType(String origin, DrivingLicence drivingLicence) {
        String[] split = origin.split(" ");

        if(split.length != 3) {
            throw new IllegalStateException("Invalid origin licence status: " + origin);
        }

        drivingLicence.setStatus(Status.fromValue(split[0]));
        drivingLicence.setType(Type.fromValue(split[1]));
    }

    private static Driver copyMinimal(Driver origin) {
        Driver result = new Driver();

        result.setId(origin.getId());
        result.setPid(origin.getPid());
        result.setCallsign(origin.getCallsign());

        return result;
    }
}
