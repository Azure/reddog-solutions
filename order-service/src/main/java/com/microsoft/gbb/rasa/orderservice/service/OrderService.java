package com.microsoft.gbb.rasa.orderservice.service;


import com.microsoft.gbb.rasa.orderservice.dto.CustomerOrderDto;
import com.microsoft.gbb.rasa.orderservice.dto.OrderSummaryDto;

/**
 * The interface Order service.
 */
public interface OrderService {
    /**
     * Create order string.
     *
     * @param order the order
     * @return the string
     */
    public OrderSummaryDto createOrder(CustomerOrderDto order);
}
