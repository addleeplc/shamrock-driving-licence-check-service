/*
 * Copyright 2008 - 2026 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.redis;

import com.haulmont.monaco.AppContext;
import com.haulmont.monaco.ServiceException;
import com.haulmont.monaco.redis.Redis;
import com.haulmont.monaco.redis.concurrent.RedisLockRegistry;
import com.haulmont.shamrock.driving.licence.check.ErrorCode;
import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import org.picocontainer.annotations.Component;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;

import java.util.function.Supplier;

@Component
public class LockRegistry {
    private Redis<String, String> redis;
    private RedisLockRegistry tokenLockRegistry;

    @Inject
    private Logger logger;

    @Inject
    private ServiceConfiguration configuration;

    public void start() {
        redis = getRedis();
        tokenLockRegistry = new RedisLockRegistry(() -> redis, "token");
    }

    public <T> T withTokenLock(Supplier<T> action) {
        return withLock(tokenLockRegistry, "token", "Token lock acquired by another process", action);
    }

    private <T> T withLock(RedisLockRegistry lockRegistry, String id, String message, Supplier<T> supplier) {
        RedisLockRegistry.Lock lock = get(id, lockRegistry);
        if (!lock.tryLock()) {
            throw new ServiceException(ErrorCode.TOKEN_LOCK_ERROR, message);
        }

        try {
            return supplier.get();
        } finally {
            try {
                lock.unlock();
            } catch (Throwable t) {
                logger.error(String.format("Can't unlock entity '%s'", lockRegistry.getName() + ":" + id), t);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Redis<String, String> getRedis() {
        return AppContext.getResources().get(configuration.getRedisResource(), Redis.class);
    }

    private RedisLockRegistry.Lock get(String id, RedisLockRegistry lockRegistry) {
        return lockRegistry.get(lockRegistry.getName() + ":" + id);
    }
}
