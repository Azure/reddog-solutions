package com.microsoft.gbb.reddog.virtualcustomers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * The type Product.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

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
        return "Product{" +
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