package com.microsoft.gbb.reddog.loyaltyservice.controller;

import com.microsoft.gbb.reddog.loyaltyservice.exception.LoyaltySaveException;
import com.microsoft.gbb.reddog.loyaltyservice.model.OrderSummary;
import com.microsoft.gbb.reddog.loyaltyservice.service.LoyaltyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoyaltyController {

    private final LoyaltyService loyaltyService;

    public LoyaltyController(LoyaltyService loyaltyService) {
        this.loyaltyService = loyaltyService;
    }

    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public String updateLoyalty(@RequestBody OrderSummary orderSummary) {
        if (null == orderSummary) {
            throw new LoyaltySaveException("Order is null");
        }
        return loyaltyService.updateLoyalty(orderSummary);
    }
}
