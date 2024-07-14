package com.github.mehrdadfalahati.redisutills.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mehrdadfalahati.redisutills.service.RedisDto;
import com.github.mehrdadfalahati.redisutills.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisServiceImpl<K, T> implements RedisService<K, T> {

    private final RedisTemplate<K, T> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void setIfAbsent(RedisDto<K> redisDto, T value) {
        getOpsForValue().setIfAbsent(redisDto.key(), value, redisDto.timeout(), redisDto.timeUnit());
    }

    @Override
    public void set(RedisDto<K> redisDto, T value) {
        getOpsForValue().set(redisDto.key(), value, redisDto.timeout(), redisDto.timeUnit());
    }

    @Override
    public T get(String cacheName, TypeReference<T> typeReference) {
        return objectMapper.convertValue(getOpsForValue().get(cacheName), typeReference);
    }

    @Override
    public void delete(K key) {
        redisTemplate.delete(key);
    }

    @Override
    public Boolean hasKey(K key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public List<T> range(K key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    private ValueOperations<K, T> getOpsForValue() {
        return redisTemplate.opsForValue();
    }
}