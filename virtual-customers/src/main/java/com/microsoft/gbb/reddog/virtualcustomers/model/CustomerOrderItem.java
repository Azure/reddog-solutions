package com.microsoft.gbb.reddog.virtualcustomers.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Customer order item.
 */
public class CustomerOrderItem {

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("productId")
    private int productId;

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets product id.
     *
     * @param productId the product id
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public int getProductId() {
        return productId;
    }
}