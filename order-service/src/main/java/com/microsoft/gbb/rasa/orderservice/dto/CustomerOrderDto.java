package com.microsoft.gbb.rasa.orderservice.dto;

import java.io.Serializable;

public record CustomerOrderDto(Long customerOrderId, String loyaltyId, String firstName, String lastName,
                               String storeId) implements Serializable {
}
