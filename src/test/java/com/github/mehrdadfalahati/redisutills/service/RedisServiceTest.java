package com.github.mehrdadfalahati.redisutills.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mehrdadfalahati.redisutills.service.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class RedisServiceTest extends AbstractRedisTestContainer {

    @Autowired
    private RedisService<String, List<Product>> redisListService;

    @Autowired
    private RedisService<String, Product> redisService;

    private final Product milk = Product.builder()
            .name("Milk")
            .price(10.2)
            .build();

    private final Product meat = Product.builder()
            .name("Meat")
            .price(100.5)
            .build();

    @Test
    void whenAddingListOfProducts_expectedGetProductsWithKey() {
        redisListService.setIfAbsent(new RedisDto<>("products", 5, TimeUnit.SECONDS), List.of(milk, meat));

        List<Product> products = redisListService.get("products", Product.getTypeReferences());
        assertEquals(2, products.size());
    }

    @Test
    void whenAddingListOfProducts_thenCallDeleteByKey_expectedGetNone() {
        redisListService.setIfAbsent(new RedisDto<>("products", 5, TimeUnit.SECONDS), List.of(milk, meat));

        redisListService.delete("products");
        List<Product> products = redisListService.get("products", Product.getTypeReferences());
        assertNull(products);
    }

    @Test
    void whenAddingProduct_thenCallDeleteByKey_expectedGetNone() {
        redisService.setIfAbsent(new RedisDto<>("products", 5, TimeUnit.SECONDS), milk);

        redisService.delete("products");
        Product products = redisService.get("products", Product.getTypeReference());
        assertNull(products);
    }


    @Test
    void whenAddingListOfProduct_expectedGetProductWithKey() {
        redisService.set(new RedisDto<>("product", 5, TimeUnit.SECONDS), milk);

        Product product = redisService.get("product", Product.getTypeReference());
        assertEquals(milk.getId(), product.getId());
    }

    @Test
    void whenAddingListOfProducts_thenCallHasKey_expectedGetTrue() {
        redisListService.setIfAbsent(new RedisDto<>("products", 5, TimeUnit.SECONDS), List.of(milk, meat));

        assertTrue(redisListService.hasKey("products"));
    }

    @Test
    void whenAddingProduct_thenCallHasKey_expectedGetTrue() {
        redisService.setIfAbsent(new RedisDto<>("product", 5, TimeUnit.SECONDS), milk);

        assertTrue(redisService.hasKey("product"));
    }
}