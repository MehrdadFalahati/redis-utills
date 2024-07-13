package com.github.mehrdadfalahati.redisutills.service;

import com.fasterxml.jackson.core.type.TypeReference;

public interface RedisService<T> {
    void setIfAbsent(RedisDto redisDto, T value);
    void set(RedisDto redisDto, T value);
    T get(String cacheName, TypeReference<T> typeReference);
    void delete(String key);
    Boolean hasKey(String key);
}
