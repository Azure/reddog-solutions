package com.microsoft.gbb.reddog.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomerOrderItemDto extends AbstractDto<String> {
    @JsonProperty("productId")
    private Long id;

    @JsonProperty("quantity")
    private int quantity;

    @Override
    public String toString() {
        return "CustomerOrderItemDto{" +
                "productId=" + id +
                ", quantity=" + quantity +
                '}';
    }
}