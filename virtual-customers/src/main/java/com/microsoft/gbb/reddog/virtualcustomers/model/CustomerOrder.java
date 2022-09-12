package com.microsoft.gbb.reddog.virtualcustomers.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * The type Customer order.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "loyaltyId='" + loyaltyId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", orderItems=" + orderItems +
                ", storeId='" + storeId + '\'' +
                '}';
    }
}