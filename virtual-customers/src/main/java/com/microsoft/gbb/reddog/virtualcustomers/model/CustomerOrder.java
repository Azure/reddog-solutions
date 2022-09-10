package com.microsoft.gbb.reddog.virtualcustomers.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Customer order.
 */
public class CustomerOrder {

    @JsonProperty("loyaltyId")
    private String loyaltyId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("OrderItems")
    private List<CustomerOrderItem> orderItems;

    @JsonProperty("storeId")
    private String storeId;

    /**
     * Sets loyalty id.
     *
     * @param loyaltyId the loyalty id
     */
    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    /**
     * Gets loyalty id.
     *
     * @return the loyalty id
     */
    public String getLoyaltyId() {
        return loyaltyId;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets order items.
     *
     * @param orderItems the order items
     */
    public void setOrderItems(List<CustomerOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * Gets order items.
     *
     * @return the order items
     */
    public List<CustomerOrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Sets store id.
     *
     * @param storeId the store id
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    /**
     * Gets store id.
     *
     * @return the store id
     */
    public String getStoreId() {
        return storeId;
    }
}