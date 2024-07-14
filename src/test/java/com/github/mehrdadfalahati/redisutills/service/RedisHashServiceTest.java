package com.github.mehrdadfalahati.redisutills.service;

import com.github.mehrdadfalahati.redisutills.service.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
public class RedisHashServiceTest extends AbstractRedisTestContainer  {

    @Autowired
    private RedisService<String, Product> redisHashService;

    @Autowired
    @Qualifier("redisHashService")
    private RedisService<String, List<Product>> redisHashListService;

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
        redisHashListService.setIfAbsent(new RedisDto<>("products", 5, TimeUnit.SECONDS), List.of(milk, meat));

        List<Product> products = redisHashListService.get("products", Product.getTypeReferences());
        assertEquals(2, products.size());
    }

    @Test
    void whenAddingListOfProducts_thenCallDeleteByKey_expectedGetNone() {
        redisHashListService.setIfAbsent(new RedisDto<>("products", 5, TimeUnit.SECONDS), List.of(milk, meat));

        redisHashListService.delete("products");
        List<Product> products = redisHashListService.get("products", Product.getTypeReferences());
        assertNull(products);
    }

    @Test
    void whenAddingProduct_thenCallDeleteByKey_expectedGetNone() {
        redisHashService.setIfAbsent(new RedisDto<>("product", 5, TimeUnit.SECONDS), milk);

        redisHashService.delete("product");
        Product product = redisHashService.get("product", Product.getTypeReference());
        assertNull(product);
    }


    @Test
    void whenAddingListOfProduct_expectedGetProductWithKey() {
        redisHashService.set(new RedisDto<>("product", 5, TimeUnit.SECONDS), milk);

        Product product = redisHashService.get("product", Product.getTypeReference());
        assertEquals(milk.getId(), product.getId());
    }

    @Test
    void whenAddingListOfProducts_thenCallHasKey_expectedGetTrue() {
        redisHashListService.setIfAbsent(new RedisDto<>("products", 5, TimeUnit.SECONDS), List.of(milk, meat));

        assertTrue(redisHashListService.hasKey("products"));
    }

    @Test
    void whenAddingProduct_thenCallHasKey_expectedGetTrue() {
        redisHashService.setIfAbsent(new RedisDto<>("product", 5, TimeUnit.SECONDS), milk);

        assertTrue(redisHashService.hasKey("product"));
    }
}
