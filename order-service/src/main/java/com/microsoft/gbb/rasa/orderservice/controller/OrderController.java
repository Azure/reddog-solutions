package com.microsoft.gbb.rasa.orderservice.controller;

import com.microsoft.gbb.rasa.orderservice.dto.CustomerOrderDto;
import com.microsoft.gbb.rasa.orderservice.dto.OrderSummaryDto;
import com.microsoft.gbb.rasa.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/order",
                 consumes = "application/json",
                 produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public ResponseEntity<OrderSummaryDto> order(@RequestBody @Valid CustomerOrderDto order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }
}
