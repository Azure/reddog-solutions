package com.microsoft.gbb.rasa.accountingservice.dto;

import java.io.Serializable;
import java.util.List;

public record CustomerOrderDto(Long customerOrderId, String loyaltyId, String firstName, String lastName,
                               String storeId, List<CustomerOrderItemDto> orderItems) implements Serializable {
}
