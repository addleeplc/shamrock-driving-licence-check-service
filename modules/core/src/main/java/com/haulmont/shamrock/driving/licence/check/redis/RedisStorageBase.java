/*
 * Copyright 2008 - 2023 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.shamrock.driving.licence.check.redis;

import com.haulmont.monaco.AppContext;
import com.haulmont.monaco.redis.Redis;
import com.haulmont.monaco.redis.cache.RedisCacheObjectCodec;
import com.haulmont.monaco.redis.cache.codec.JacksonObjectCodec;
import com.haulmont.shamrock.driving.licence.check.ServiceConfiguration;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RedisStorageBase<KeyType, ValueType, K extends Key<KeyType>> {

    @Inject
    protected Logger log;
    @Inject
    protected ServiceConfiguration configuration;

    private Redis<String, String> redis;

    protected final RedisCacheObjectCodec<ValueType> valueCodec;
    protected final KeyCodec<KeyType> keyCodec;

    private final Class<ValueType> valueClass;

    protected final Function<ServiceConfiguration, Long> ttlProvider;

    public RedisStorageBase(Class<ValueType> valueClass, Function<ServiceConfiguration, Long> ttlProvider) {

        this.valueClass = valueClass;
        //noinspection unchecked
        this.keyCodec = KeyCodecFactory.construct(valueClass);

        valueCodec = new JacksonObjectCodec<>(valueClass);
        this.ttlProvider = ttlProvider;
    }

    public Optional<ValueType> get(K key) {
        if (key.isPattern()) throw new IllegalArgumentException("pattern isn't allowed in this method");

        log.debug("fetch {} from redis by key {}", valueClass.getSimpleName(), keyCodec.encode(key));
        return Optional.ofNullable(getRedis().get(keyCodec.encode(key)))
                .map(valueCodec::decode);
    }

    public List<ValueType> getAll(K key) {
        return getByPattern(keyCodec.encode(key));
    }

    public List<ValueType> getAll() {
        return getByPattern(keyCodec.keyPattern());
    }

    private List<ValueType> getByPattern(String keyPattern) {
        return getRedis()
                .keys(keyPattern)
                .stream()
                .map(getRedis()::get)
                .filter(Objects::nonNull)
                .filter(StringUtils::isNotBlank)
                .map(valueCodec::decode)
                .collect(Collectors.toList());
    }

    public void save(K key, ValueType valueType) {
        save(key, valueType, ttlProvider.apply(configuration));
    }

    public void save(K key, ValueType valueType, Long ttlSeconds) {
        if (key.isPattern()) throw new IllegalArgumentException("pattern isn't allowed in this method");

        getRedis().setex(keyCodec.encode(key), ttlSeconds, valueCodec.encode(valueType));
        log.debug("redis save {} with key {}}", valueClass.getSimpleName(), keyCodec.encode(key));
    }

    public void delete(K key) {
        if (key.isPattern()) throw new IllegalArgumentException("pattern isn't allowed in this method");

        delete(keyCodec.encode(key));
    }

    public void deleteAll(K key) {
        deleteByPattern(keyCodec.encode(key));
    }

    private void delete(String key) {
        Long deletedCount = getRedis().del(key);

        if (deletedCount == null || deletedCount <= 0) return;

        log.debug("{} for key {} has been removed from redis", valueClass.getSimpleName(), key);
    }

    private void deleteByPattern(String keyPattern) {
        getRedis()
                .keys(keyPattern)
                .forEach(this::delete);
    }

    public void expire(K key, Long ttl) {
        Boolean expired = getRedis().expire(keyCodec.encode(key), ttl);

        if (BooleanUtils.isTrue(expired)) {
            log.debug("Key {} has been scheduled for removal, ttl: {}", key, ttl);
        }
    }

    protected List<String> keys(String pattern){
        return getRedis().keys(pattern);
    }

    public Boolean exist(K key){
        return getRedis().exists(keyCodec.encode(key));
    }

    public Boolean notExist(K key){
        return !exist(key);
    }


    public Redis<String, String> getRedis() {
        if (redis == null) {
            //noinspection unchecked
            redis = AppContext.getResources().get(configuration.getRedisResource(), Redis.class);
        }

        return redis;
    }

}
