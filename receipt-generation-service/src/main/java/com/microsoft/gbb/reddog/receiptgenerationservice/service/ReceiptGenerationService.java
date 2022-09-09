package com.microsoft.gbb.reddog.receiptgenerationservice.service;

import com.microsoft.gbb.reddog.receiptgenerationservice.model.OrderSummary;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Receipt generation service
 */
@Slf4j
@Component
public class ReceiptGenerationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptGenerationService.class);

    public ReceiptGenerationService() {
    }


    public String generateReceipt(OrderSummary orderSummary) {
        LOGGER.info("Generating receipt");
        // TODO: write blob to storage
        return "WIP to Sccuessfully generated receipt";
    }

}
