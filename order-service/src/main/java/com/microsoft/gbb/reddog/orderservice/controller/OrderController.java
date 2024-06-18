package com.microsoft.gbb.reddog.orderservice.controller;

import com.microsoft.gbb.reddog.orderservice.dto.CustomerOrderDto;
import com.microsoft.gbb.reddog.orderservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
