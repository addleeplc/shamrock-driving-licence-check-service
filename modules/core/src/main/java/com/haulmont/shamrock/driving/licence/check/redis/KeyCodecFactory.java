/*
 * Copyright 2008 - 2023 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.redis;

import com.haulmont.shamrock.driving.licence.check.redis.keyCodec.RedisCacheSingleKeyCode;
import com.haulmont.shamrock.driving.licence.check.storage.MandateFormContext;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static java.util.Map.entry;

public class KeyCodecFactory {
    private static final String DRIVING_LICENCE_CHECK_SERVICE = "shamrock-driving-licence-check-service";
    private static final String MANDATE_FORM = DRIVING_LICENCE_CHECK_SERVICE + ":mandateForm";


    private static final Map<Class<?>, Supplier<RedisCacheKeyCodec<UUID>>> class2keyCodec = Map.ofEntries(
        entry(MandateFormContext.class, () -> new RedisCacheSingleKeyCode<>(MANDATE_FORM, ""))
    );

    /**
     * create instance of {@link RedisCacheKeyCodec} depend on giving class.
     * @param clazz class of entity that should saved in redis.
     * @return instance of {@link  RedisCacheKeyCodec} for giving class
     */
    @SuppressWarnings({"rawtypes"})
    public static RedisCacheKeyCodec construct(Class clazz){
        return Optional.ofNullable(class2keyCodec.get(clazz))
                .map(Supplier::get)
                .orElseThrow(IllegalArgumentException::new);
    }

}
