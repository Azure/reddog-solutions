package com.microsoft.gbb.reddog.loyaltyservice.controller;

import com.microsoft.gbb.reddog.loyaltyservice.exception.LoyaltySaveException;
import com.microsoft.gbb.reddog.loyaltyservice.model.OrderSummary;
import com.microsoft.gbb.reddog.loyaltyservice.service.LoyaltyService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class LoyaltyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoyaltyController.class);
    private final LoyaltyService loyaltyService;

    public LoyaltyController(LoyaltyService loyaltyService) {
        this.loyaltyService = loyaltyService;
    }

    @KafkaListener(topics = "reddog.orders" , groupId = "${'${data.topic.group}'")
    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public String updateLoyalty(@RequestBody OrderSummary orderSummary) {
        LOGGER.info("Received order summary: " + orderSummary);
        if (null == orderSummary) {
            throw new LoyaltySaveException("Order is null");
        }
        return loyaltyService.updateLoyalty(orderSummary);
    }
}
