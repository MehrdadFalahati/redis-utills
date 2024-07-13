package com.github.mehrdadfalahati.redisutills.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mehrdadfalahati.redisutills.service.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class RedisListServiceTest extends AbstractRedisTestContainer {

    @Autowired
    private RedisService<List<Product>> redisListService;

    @Autowired
    private RedisService<Product> redisService;

    private Product milk = Product.builder()
            .name("Milk")
            .price(10.2)
            .build();

    private Product meat = Product.builder()
            .name("Meat")
            .price(100.5)
            .build();

    @Test
    void whenAddingListOfProducts_expectedGetProductsWithKey() {
        redisListService.setIfAbsent(new RedisDto("products", 5, TimeUnit.SECONDS), List.of(milk, meat));

        List<Product> products = redisListService.get("products", Product.getTypeReference());
        assertEquals(2, products.size());
    }

    @Test
    void whenAddingListOfProducts_thenCallDeleteByKey_expectedGetNone() {
        redisListService.setIfAbsent(new RedisDto("products", 5, TimeUnit.SECONDS), List.of(milk, meat));

        redisListService.delete("products");
        List<Product> products = redisListService.get("products", Product.getTypeReference());
        assertNull(products);
    }

    @Test
    void whenAddingListOfProduct_expectedGetProductWithKey() {
        redisService.setIfAbsent(new RedisDto("product", 5, TimeUnit.SECONDS), milk);

        Product product = redisService.get("product", new TypeReference<Product>() {});
        assertEquals(milk.getId(), product.getId());
    }

    @Test
    void whenAddingListOfProducts_thenCallHasKey_expectedGetTrue() {
        redisListService.setIfAbsent(new RedisDto("products", 5, TimeUnit.SECONDS), List.of(milk, meat));

        assertTrue(redisListService.hasKey("products"));
    }
}