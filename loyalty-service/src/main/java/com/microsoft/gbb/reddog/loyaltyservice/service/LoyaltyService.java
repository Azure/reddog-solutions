package com.microsoft.gbb.reddog.loyaltyservice.service;

import com.microsoft.gbb.reddog.loyaltyservice.dto.OrderSummaryDto;
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

    public LoyaltyService() {
    }

    public String updateLoyalty(OrderSummaryDto orderSummary) {
        log.info("Updating loyalty for order: " + orderSummary);
        // TODO: invoke loyalty state store to update entry in DB
        return "Work in progress, try again";
    }

}
