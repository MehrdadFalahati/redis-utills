package com.github.mehrdadfalahati.redisutills.service;

import com.github.mehrdadfalahati.redisutills.RedisTestConfiguration;
import com.github.mehrdadfalahati.redisutills.service.dto.Product;
import com.redis.testcontainers.RedisContainer;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
@SpringBootTest(classes = RedisTestConfiguration.class)
public abstract class AbstractRedisTestContainer {

    protected static final RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:5.0.3-alpine"))
            .withExposedPorts(6379);

    static {
        REDIS_CONTAINER.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("DockerContainer stop");
            REDIS_CONTAINER.stop();
        }));
    }

    @DynamicPropertySource
    private static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379)
                .toString());
    }

    protected final Product milk = Product.builder()
            .name("Milk")
            .price(10.2)
            .build();

    protected final Product meat = Product.builder()
            .name("Meat")
            .price(100.5)
            .build();

    @Test
    void givenRedisContainerConfiguredWithDynamicProperties_whenCheckingRunningStatus_thenStatusIsRunning() {
        assertTrue(REDIS_CONTAINER.isRunning());
    }
}
