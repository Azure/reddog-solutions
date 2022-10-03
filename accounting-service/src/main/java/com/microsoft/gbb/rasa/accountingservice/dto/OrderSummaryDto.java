package com.microsoft.gbb.rasa.accountingservice.dto;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * The type Order summary.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Container(containerName="reddog", ru="400")
public class OrderSummaryDto extends AbstractDto<String> {

    @JsonProperty("orderCompletedDate")
    private Date orderCompletedDate;

    @JsonProperty("loyaltyId")
    @PartitionKey
    private String loyaltyId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("orderId")
    @Id
    private String orderId;

    @JsonProperty("storeId")
    private String storeId;

    @JsonProperty("orderDate")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @JsonProperty("orderDateInstant")
    private Long orderDateInstant;

    @JsonProperty("orderItems")
    private List<OrderItemSummaryDto> orderItems;

    @JsonProperty("orderTotal")
    private double orderTotal;

    @Override
    public String toString() {
        return "OrderSummaryDto{" +
                "orderCompletedDate=" + orderCompletedDate +
                ", loyaltyId='" + loyaltyId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", orderId=" + orderId +
                ", storeId='" + storeId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderItems=" + orderItems +
                ", orderTotal=" + orderTotal +
                '}';
    }
}