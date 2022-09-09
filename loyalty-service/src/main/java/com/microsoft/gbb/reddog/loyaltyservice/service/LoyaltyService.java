package com.microsoft.gbb.reddog.loyaltyservice.service;

import com.microsoft.gbb.reddog.loyaltyservice.model.OrderSummary;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Receipt generation service
 */
@Slf4j
@Component
public class LoyaltyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoyaltyService.class);

    public LoyaltyService() {
    }

    public String updateLoyalty(OrderSummary orderSummary) {
        LOGGER.info("Generating receipt");
        // TODO: invoke loyalty state store to update entry in DB
        return "Work in progress, try again";
    }

}
