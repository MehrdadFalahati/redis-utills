package com.github.mehrdadfalahati.redisutills.service.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Builder.Default
    private final String id = UUID.randomUUID().toString();
    private String name;
    private Double price;
    @Builder.Default
    private Instant createAt = Instant.now();

    public static TypeReference<List<Product>> getTypeReference() {
        return new TypeReference<List<Product>>() {
        };
    }
}
