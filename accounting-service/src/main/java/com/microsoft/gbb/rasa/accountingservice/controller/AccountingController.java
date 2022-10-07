package com.microsoft.gbb.rasa.accountingservice.controller;

import com.microsoft.gbb.rasa.accountingservice.dto.OrderSummaryDto;
import com.microsoft.gbb.rasa.accountingservice.dto.OrdersTimeSeries;
import com.microsoft.gbb.rasa.accountingservice.service.AccountingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountingController {

    private final AccountingService accountingService;

    public AccountingController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    @GetMapping(value = "/orders")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<OrderSummaryDto>> getAllInFlightOrders() {
        return ResponseEntity.ok(accountingService.findAllInflightOrders());
    }

    @GetMapping(value = "/orders/{period}/{timeSpan}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<OrdersTimeSeries> getOrdersByPeriod(@PathVariable String period,
                                                                    @PathVariable String timeSpan,
                                                                    @RequestHeader("store-id") String storeId) {
        return ResponseEntity.ok(accountingService.getOrderCountOverTime(period, timeSpan, storeId));
    }
}
