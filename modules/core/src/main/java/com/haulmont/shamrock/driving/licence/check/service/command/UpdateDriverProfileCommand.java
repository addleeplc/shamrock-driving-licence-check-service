/*
 * Copyright 2008 - 2025 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.service.command;

import com.haulmont.monaco.mybatis.MyBatisCommand;
import com.haulmont.monaco.mybatis.SqlSessionFactoryResource;
import com.haulmont.shamrock.driving.licence.check.dto.checked_safe.webhook.Conviction;
import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.haulmont.shamrock.driving.licence.check.mybatis.SqlSessionFactory.NS_PREFIX;
import static java.util.Map.entry;

public class UpdateDriverProfileCommand extends MyBatisCommand<Void> {

    private final Long shamrockUserId;
    private final UUID driverId;
    private final Integer licencePoints;
    private final DateTime licenceInspectDate;
    private final List<Conviction> convictions;

    public UpdateDriverProfileCommand(SqlSessionFactoryResource sqlSessionFactory, Long shamrockUserId, UUID driverId, Integer licencePoints, DateTime licenceInspectDate, List<Conviction> convictions) {
        super(sqlSessionFactory);
        this.shamrockUserId = shamrockUserId;
        this.driverId = driverId;
        this.licencePoints = licencePoints;
        this.licenceInspectDate = licenceInspectDate;
        this.convictions = convictions;
    }

    @Override
    protected Void __execute(SqlSession sqlSession) {
        sqlSession.update(NS_PREFIX + getName(),
                Map.ofEntries(
                        entry("licencePoints", licencePoints),
                        entry("licenceInspectDate", licenceInspectDate),
                        entry("driverId", driverId),
                        entry("convictions", convictions),
                        entry("createdBy", shamrockUserId)
                )
        );
        return null;
    }

    @Override
    protected String getName() {
        return "updateDriverProfile";
    }
}