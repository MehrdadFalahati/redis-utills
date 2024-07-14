package com.github.mehrdadfalahati.redisutills.service;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public interface RedisService<K, V> {
    void setIfAbsent(RedisDto<K> redisDto, V value);
    void set(RedisDto<K> redisDto, V value);
    V get(String cacheName, TypeReference<V> typeReference);
    void delete(K key);
    Boolean hasKey(K key);
    List<V> range(K key, int start, int end);
}
