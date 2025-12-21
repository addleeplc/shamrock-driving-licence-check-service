/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command;

import com.haulmont.monaco.mybatis.MyBatisCommand;
import com.haulmont.monaco.mybatis.SqlSessionFactoryResource;
import com.haulmont.shamrock.driving.licence.check.mq.dto.Conviction;
import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;

import java.util.*;

import static com.haulmont.shamrock.driving.licence.check.mybatis.SqlSessionFactory.NS_PREFIX;
import static java.util.Map.entry;

public class PersistCheckResultCommand extends MyBatisCommand<Void> {

    private final Long shamrockUserId;
    private final Long driverPid;
    private final Integer licencePoints;
    private final DateTime licenceInspectionDate;
    private final List<Conviction> convictions;

    public PersistCheckResultCommand(SqlSessionFactoryResource sqlSessionFactory, Long shamrockUserId, Long driverPid, Integer licencePoints, DateTime licenceInspectionDate, List<Conviction> convictions) {
        super(sqlSessionFactory);
        this.shamrockUserId = shamrockUserId;
        this.driverPid = driverPid;
        this.licencePoints = licencePoints;
        this.licenceInspectionDate = licenceInspectionDate;
        this.convictions = convictions;
    }

    @Override
    protected Void __execute(SqlSession sqlSession) {
        sqlSession.update(NS_PREFIX + getName(),
                Map.ofEntries(
                        entry("licencePoints", licencePoints),
                        entry("licenceInspectionDate", licenceInspectionDate),
                        entry("driverPid", driverPid),
                        entry("convictions", convictions),
                        entry("createdBy", shamrockUserId)
                )
        );
        return null;
    }

    @Override
    protected String getName() {
        return "updateDrivingLicence";
    }
}