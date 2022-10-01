package com.microsoft.gbb.rasa.makelineservice.service;

import com.microsoft.gbb.rasa.makelineservice.dto.OrderSummaryDto;
import com.microsoft.gbb.rasa.makelineservice.messaging.KafkaPublisher;
import com.microsoft.gbb.rasa.makelineservice.repository.OrderSummaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * The type Order service.
 */
@Slf4j
@Component
public class MakelineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MakelineService.class);
    private final KafkaPublisher kafkaPublisher;
    private final OrderSummaryRepository orderSummaryRepository;

    public MakelineService(KafkaPublisher kafkaPublisher, OrderSummaryRepository orderSummaryRepository) {
        this.kafkaPublisher = kafkaPublisher;
        this.orderSummaryRepository = orderSummaryRepository;
    }

    public OrderSummaryDto addOrderToMakeLine(OrderSummaryDto orderSummary) {
        LOGGER.info("Adding order to makeline");
        // TODO: Extract dates to seperate attributes for range queries
        return orderSummaryRepository.save(orderSummary);
        // List<OrderSummaryDto> orderSummaries = new ArrayList<OrderSummaryDto>();
        // TODO: Get all orders from orderSummary.storeId
        // append a new order
        // save state to a new persistent store for java via daprClient
    }

    public List<OrderSummaryDto> getOrders(String storeId) {
        LOGGER.info("Getting all orders for storeId: " + storeId);
        return orderSummaryRepository.findAllByStoreId(storeId);
    }

    public String completeOrder(String storeId, String orderId) {
        LOGGER.info("Completing order for storeId: " + storeId + " orderId: " + orderId);
        // TODO: Set Order completed date
        // Publish event to order completed topic using daprClient
        // remove order from state store using daprClient
        return "WIP";
    }
}
