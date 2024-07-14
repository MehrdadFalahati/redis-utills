package com.github.mehrdadfalahati.redisutills.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig<K, V> {

    @Bean
    public RedisTemplate<K, V> redisTemplate(RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory, jsonRedisSerializer());
    }

    @Bean
    public GenericJackson2JsonRedisSerializer jsonRedisSerializer() {
        return createJsonRedisSerializer();
    }


    private RedisTemplate<K, V> createRedisTemplate(RedisConnectionFactory connectionFactory, GenericJackson2JsonRedisSerializer serializer) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);

        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setEnableDefaultSerializer(true);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private GenericJackson2JsonRedisSerializer createJsonRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}
