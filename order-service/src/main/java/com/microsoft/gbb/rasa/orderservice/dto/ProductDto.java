package com.microsoft.gbb.rasa.orderservice.dto;

import java.io.Serializable;

public record ProductDto(String productName, String description, String categoryId, double unitPrice, Long productId,
                         double unitCost, String imageUrl) implements Serializable {
}
