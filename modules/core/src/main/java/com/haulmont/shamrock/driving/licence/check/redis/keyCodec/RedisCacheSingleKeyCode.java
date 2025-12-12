/*
 * Copyright 2008 - 2023 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.redis.keyCodec;

import com.haulmont.shamrock.driving.licence.check.redis.RedisCacheKeyCodec;

public class RedisCacheSingleKeyCode<KeyType> extends RedisCacheKeyCodec<KeyType> {

    public RedisCacheSingleKeyCode(String startPrefix, String endPrefix) {
        super(startPrefix
                + GROUP_DELIMITER
                + PARAMETER_PLACEHOLDER
                + GROUP_DELIMITER
                + endPrefix);
    }


}
