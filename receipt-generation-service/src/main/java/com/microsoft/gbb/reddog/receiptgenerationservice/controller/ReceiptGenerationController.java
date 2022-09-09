package com.microsoft.gbb.reddog.receiptgenerationservice.controller;

import com.microsoft.gbb.reddog.receiptgenerationservice.exception.ReceiptSaveException;
import com.microsoft.gbb.reddog.receiptgenerationservice.model.OrderSummary;
import com.microsoft.gbb.reddog.receiptgenerationservice.service.ReceiptGenerationService;
import io.opentelemetry.extension.annotations.WithSpan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReceiptGenerationController {

    private final ReceiptGenerationService receiptGenerationService;

    public ReceiptGenerationController(ReceiptGenerationService receiptGenerationService) {
        this.receiptGenerationService = receiptGenerationService;
    }

    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public String generateReceipt(@RequestBody OrderSummary orderSummary) {
        if (null == orderSummary) {
            throw new ReceiptSaveException("Order is null");
        }
        return receiptGenerationService.generateReceipt(orderSummary);
    }
}
