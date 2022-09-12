package com.microsoft.gbb.reddog.virtualcustomers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * The type Customer order item.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderItem {

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("productId")
    private int productId;

    @Override
    public String toString() {
        return "CustomerOrderItem{" +
                "quantity=" + quantity +
                ", productId=" + productId +
                '}';
    }
}