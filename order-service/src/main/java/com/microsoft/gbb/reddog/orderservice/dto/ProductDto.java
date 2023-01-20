package com.microsoft.gbb.reddog.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ProductDto extends AbstractDto<String>{
    @JsonProperty("unitPrice")
    private double unitPrice;

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("unitCost")
    private double unitCost;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("categoryId")
    private String categoryId;

    @Override
    public String toString() {
        return "ProductDto{" +
                "unitPrice=" + unitPrice +
                ", productId=" + productId +
                ", unitCost=" + unitCost +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", productName='" + productName + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}