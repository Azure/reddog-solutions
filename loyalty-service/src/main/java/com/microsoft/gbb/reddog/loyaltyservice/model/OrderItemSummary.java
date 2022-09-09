package com.microsoft.gbb.reddog.loyaltyservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Order item summary.
 */
@Getter
@Setter
@Builder
public class OrderItemSummary {

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
        return "OrderItemSummary{" +
                "unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", productId=" + productId +
                ", unitCost=" + unitCost +
                ", imageUrl='" + imageUrl + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}