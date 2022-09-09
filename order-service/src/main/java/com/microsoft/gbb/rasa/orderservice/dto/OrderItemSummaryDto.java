package com.microsoft.gbb.rasa.orderservice.dto;

import lombok.Builder;

import java.io.Serializable;
import java.util.Collection;

@Builder
public record OrderItemSummaryDto(String productName, Collection<ProductDto> products, int quantity, double unitCost,
                                  String imageUrl, OrderSummaryDto orderSummary,
                                  Long orderSummaryId) implements Serializable {
}
