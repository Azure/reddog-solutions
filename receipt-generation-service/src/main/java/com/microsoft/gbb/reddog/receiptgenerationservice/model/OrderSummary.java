package com.microsoft.gbb.reddog.receiptgenerationservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type Order summary.
 */
@Builder
@Getter
@Setter
public class OrderSummary {

    @JsonProperty("orderCompletedDate")
    private LocalDate orderCompletedDate;

    @JsonProperty("loyaltyId")
    private String loyaltyId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("orderId")
    private UUID orderId;

    @JsonProperty("storeId")
    private String storeId;

    @JsonProperty("orderDate")
    private LocalDateTime orderDate;

    @JsonProperty("orderItems")
    private List<OrderItemSummary> orderItems;

    @JsonProperty("orderTotal")
    private double orderTotal;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("storeLatitude")
    private String storeLatitude;

    @JsonProperty("storeLongitude")
    private String storeLongitude;

    @Override
    public String toString() {
        return "OrderSummary{" +
                "orderCompletedDate='" + orderCompletedDate + '\'' +
                ", loyaltyId='" + loyaltyId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", orderId=" + orderId +
                ", storeId='" + storeId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderItems=" + orderItems +
                ", orderTotal=" + orderTotal +
                ", origin='" + origin + '\'' +
                ", storeLatitude='" + storeLatitude + '\'' +
                ", storeLongitude='" + storeLongitude + '\'' +
                '}';
    }
}