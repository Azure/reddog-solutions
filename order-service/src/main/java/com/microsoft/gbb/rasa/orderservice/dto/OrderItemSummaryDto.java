package com.microsoft.gbb.rasa.orderservice.dto;

import java.io.Serializable;
import java.util.Collection;

public record OrderItemSummaryDto(String productName, Collection<ProductDto> products, int quantity, double unitCost,
                                  String imageUrl, OrderSummaryDto orderSummary,
                                  Long orderSummaryId) implements Serializable {
}
