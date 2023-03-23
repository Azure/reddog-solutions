package com.microsoft.gbb.reddog.virtualcustomers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomerOrderDto extends AbstractDto<String> {
    @JsonProperty("customerId")
    private Long customerId;

    @JsonProperty("loyaltyId")
    private String loyaltyId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("storeId")
    private String storeId;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("storeLatitude")
    private String storeLatitude;

    @JsonProperty("storeLongitude")
    private String storeLongitude;

    @JsonProperty("orderItems")
    private List<CustomerOrderItemDto> orderItems;

    @Override
    public String toString() {
        return "CustomerOrderDto{" +
                "customerId=" + customerId +
                ", loyaltyId='" + loyaltyId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", storeId='" + storeId + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}

// TODO: investigate with newer records support
//public record CustomerOrderDto(Long customerOrderId, String loyaltyId, String firstName, String lastName,
//                               String storeId, List<CustomerOrderItemDto> orderItems) implements Serializable {
//}
