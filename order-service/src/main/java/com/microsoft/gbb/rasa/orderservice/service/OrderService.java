package com.microsoft.gbb.rasa.orderservice.service;


import com.microsoft.gbb.rasa.orderservice.entities.CustomerOrder;
import com.microsoft.gbb.rasa.orderservice.entities.OrderSummary;

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
    public OrderSummary createOrder(CustomerOrder order);
}
