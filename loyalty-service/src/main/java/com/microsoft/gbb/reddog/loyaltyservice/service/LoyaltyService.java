package com.microsoft.gbb.reddog.loyaltyservice.service;

import com.microsoft.gbb.reddog.loyaltyservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.loyaltyservice.model.LoyaltySummary;
import com.microsoft.gbb.reddog.loyaltyservice.repository.LoyaltySummaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Loyalty Summary Service
 */
@Slf4j
@Component
public class LoyaltyService {

    private final LoyaltySummaryRepository loyaltySummaryRepository;
    public LoyaltyService(LoyaltySummaryRepository loyaltySummaryRepository) {
        this.loyaltySummaryRepository = loyaltySummaryRepository;
    }

    public LoyaltySummary updateLoyalty(OrderSummaryDto orderSummary) {
        log.info("Updating loyalty for order: " + orderSummary);
        if (orderSummary.getOrderTotal() == 0) {
            log.info("Order total is 0, no loyalty points to be added");
            throw new IllegalArgumentException("Order total is 0, no loyalty points to be added");
        }
        int loyaltyPoints = calculateLoyaltyPoints(orderSummary);
        LoyaltySummary loyaltySummary = LoyaltySummary.builder()
                .loyaltyId(orderSummary.getLoyaltyId())
                .firstName(orderSummary.getFirstName())
                .lastName(orderSummary.getLastName())
                .pointsEarned(loyaltyPoints)
                .pointTotal(loyaltyPoints)
                .build();
        log.info("Saving loyalty summary: " + loyaltySummary);
        Long result = loyaltySummaryRepository.save(loyaltySummary).block();
        log.info("Loyalty summary saved: " + result);
        return loyaltySummary;
    }

    private static int calculateLoyaltyPoints(OrderSummaryDto orderSummary) {
        return BigDecimal
                .valueOf(orderSummary.getOrderTotal())
                .multiply(new BigDecimal(10))
                .intValue();
    }

}
