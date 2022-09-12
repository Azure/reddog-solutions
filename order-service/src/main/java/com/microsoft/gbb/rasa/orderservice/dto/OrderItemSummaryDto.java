package com.microsoft.gbb.rasa.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Order item Summary DTO
 * TODO: Troubleshoot Java Records Jackson issue
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemSummaryDto {

    @JsonProperty("unitPrice")
    private double unitPrice;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("unitCost")
    private double unitCost;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("productName")
    private String productName;

    @Override
    public String toString() {
        return "OrderItemSummaryDto{" +
                "unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", productId=" + productId +
                ", unitCost=" + unitCost +
                ", imageUrl='" + imageUrl + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}