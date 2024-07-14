package com.github.mehrdadfalahati.redisutills.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mehrdadfalahati.redisutills.service.RedisDto;
import com.github.mehrdadfalahati.redisutills.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("redisHashService")
@RequiredArgsConstructor
public class RedisHashServiceImpl<K, V> implements RedisService<K, V> {

    private final RedisTemplate<K, V> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void setIfAbsent(RedisDto<K> redisDto, V value) {
        redisTemplate.expire(redisDto.key(), redisDto.timeout(), redisDto.timeUnit());
        getOpsForHash().putIfAbsent(redisDto.key(), redisDto.key(), value);
    }

    @Override
    public void set(RedisDto<K> redisDto, V value) {
        redisTemplate.expire(redisDto.key(), redisDto.timeout(), redisDto.timeUnit());
        getOpsForHash().put(redisDto.key(), redisDto.key(), value);
    }

    @Override
    public V get(K key, TypeReference<V> typeReference) {
        return objectMapper.convertValue(getOpsForHash().get(key, key), typeReference);
    }

    @Override
    public void delete(K key) {
        redisTemplate.delete(key);
    }

    @Override
    public Boolean hasKey(K key) {
        return redisTemplate.hasKey(key);
    }

    private HashOperations<K, Object, Object> getOpsForHash() {
        return redisTemplate.opsForHash();
    }
}
