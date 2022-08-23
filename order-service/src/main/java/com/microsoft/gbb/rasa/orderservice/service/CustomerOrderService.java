package com.microsoft.gbb.rasa.orderservice.service;

import com.microsoft.gbb.rasa.orderservice.entities.CustomerOrder;
import com.microsoft.gbb.rasa.orderservice.messaging.TopicProducer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * The type Order service.
 */
@Slf4j
@Component
@Qualifier("customerorder")
public class CustomerOrderService implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderService.class);
    private final TopicProducer topicProducer;

    public CustomerOrderService(TopicProducer topicProducer) {
        this.topicProducer = topicProducer;
    }
    /**
     * Create order for customer.
     *
     * @param order the order
     * @return the string
     */
    public String createOrder(CustomerOrder order) {
        LOGGER.info("Creating order");
        topicProducer.send(order.toString());
        return "Order created successfully";
    }
}
