package com.microsoft.gbb.rasa.orderservice.dto;

import java.io.Serializable;
import java.util.Date;

public record OrderSummaryDto(Long orderId, Date orderCompletedDate, String loyaltyId, String firstName,
                              String lastName, String storeId, Date orderDate,
                              double orderTotal) implements Serializable {
}
