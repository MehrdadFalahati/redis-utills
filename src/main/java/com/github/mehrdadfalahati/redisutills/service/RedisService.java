package com.github.mehrdadfalahati.redisutills.service;

import com.fasterxml.jackson.core.type.TypeReference;

public interface RedisService<K, V> {
    void setIfAbsent(RedisDto<K> redisDto, V value);
    void set(RedisDto<K> redisDto, V value);
    V get(K key, TypeReference<V> typeReference);
    void delete(K key);
    Boolean hasKey(K key);
}
