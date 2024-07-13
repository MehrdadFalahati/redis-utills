package com.github.mehrdadfalahati.redisutills.service;

import java.util.concurrent.TimeUnit;

public record RedisDto(String key, long timeout, TimeUnit timeUnit) {
}
