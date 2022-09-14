package com.microsoft.gbb.rasa.accountingservice.controller;

import com.microsoft.gbb.rasa.accountingservice.dto.OrderSummaryDto;
import com.microsoft.gbb.rasa.accountingservice.entities.CustomerOrder;
import com.microsoft.gbb.rasa.accountingservice.service.AccountingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountingController {

    private final AccountingService accountingService;

    public AccountingController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    @PostMapping(value = "/todo",
                 consumes = "application/json",
                 produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public ResponseEntity<OrderSummaryDto> order(@Valid @RequestBody CustomerOrder order) {
        return ResponseEntity.noContent().build();
    }
}
