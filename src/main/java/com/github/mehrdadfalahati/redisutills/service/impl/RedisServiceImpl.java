package com.github.mehrdadfalahati.redisutills.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mehrdadfalahati.redisutills.service.RedisDto;
import com.github.mehrdadfalahati.redisutills.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisServiceImpl<T> implements RedisService<T> {

    private final RedisTemplate<String, T> redisTemplate;
    private final ObjectMapper objectMapper;

    public void setIfAbsent(RedisDto redisDto, T value) {
        redisTemplate.opsForValue().setIfAbsent(redisDto.key(), value, redisDto.timeout(), redisDto.timeUnit());
    }

    public void set(RedisDto redisDto, T value) {
        redisTemplate.opsForValue().set(redisDto.key(), value, redisDto.timeout(), redisDto.timeUnit());
    }

    public T get(String cacheName, TypeReference<T> typeReference) {
        return objectMapper.convertValue(redisTemplate.opsForValue().get(cacheName), typeReference);
    }


    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}