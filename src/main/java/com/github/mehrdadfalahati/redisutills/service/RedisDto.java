package com.github.mehrdadfalahati.redisutills.service;

import java.util.concurrent.TimeUnit;

public record RedisDto<K>(K key, long timeout, TimeUnit timeUnit) {
}
