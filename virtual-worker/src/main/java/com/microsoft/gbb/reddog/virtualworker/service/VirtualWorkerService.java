package com.microsoft.gbb.reddog.virtualworker.service;

import com.microsoft.gbb.reddog.virtualworker.model.OrderSummary;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * The type Order service.
 */
@Slf4j
@Component
public class VirtualWorkerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VirtualWorkerService.class);
    public static final String MAKELINE_SVC_APP_ID = "make-line-service";

    public VirtualWorkerService() {
        ArrayList<OrderSummary> orders = getOrders();
        // TODO: For each order complete order after a short delay
        orders.forEach(this::completeOrder);
    }

    public void checkOrders() {
        LOGGER.info("Check Orders");

    }

    public ArrayList<OrderSummary> getOrders() {
        // TODO: get orders from makeline via WebClient
        return new ArrayList<>();
    }

    public void completeOrder(OrderSummary orderSummary) {
        // TODO: Invoke makeline delete method via WebClient
    }
}
