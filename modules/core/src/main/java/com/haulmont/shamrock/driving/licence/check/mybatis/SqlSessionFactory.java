/*
 * Copyright  2020 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.mybatis;

import com.haulmont.monaco.mybatis.SqlSessionFactoryResource;
import org.picocontainer.annotations.Component;

@Component
public class SqlSessionFactory extends SqlSessionFactoryResource {
    public static final String NS_PREFIX = "com.haulmont.shamrock.driving.licence.check.mappers.";

    public SqlSessionFactory() {
        super("shamrock-driving-licence-check-service-mybatis.xml");
    }
}
