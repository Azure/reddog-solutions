package com.microsoft.gbb.reddog.orderservice.service;


import com.microsoft.gbb.reddog.orderservice.dto.CustomerOrderDto;
import com.microsoft.gbb.reddog.orderservice.dto.OrderSummaryDto;

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
