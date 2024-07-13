package com.github.mehrdadfalahati.redisutills.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisService<T> {

    private final RedisTemplate<String, List<T>> redisTemplate;
    private final ObjectMapper objectMapper;

    public void setIfAbsent(RedisDto redisDto, List<T> value) {
        redisTemplate.opsForValue().setIfAbsent(redisDto.key(), value, redisDto.timeout(), redisDto.timeUnit());
    }

    public void set(RedisDto redisDto, List<T> value) {
        redisTemplate.opsForValue().set(redisDto.key(), value, redisDto.timeout(), redisDto.timeUnit());
    }

    public List<T> get(String cacheName, TypeReference<List<T>> typeReference) {
        return objectMapper.convertValue(redisTemplate.opsForValue().get(cacheName), typeReference);
    }


    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public record RedisDto(String key, long timeout, TimeUnit timeUnit) {
    }
}