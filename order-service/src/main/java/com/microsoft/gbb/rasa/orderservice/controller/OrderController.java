package com.microsoft.gbb.rasa.orderservice.controller;

import com.microsoft.gbb.rasa.orderservice.entities.CustomerOrder;
import com.microsoft.gbb.rasa.orderservice.exception.OrderNotFoundException;
import com.microsoft.gbb.rasa.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
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
    public String order(@RequestBody CustomerOrder order) {
        if (null == order) {
            throw new OrderNotFoundException("Order is null");
        }
        // TODO: asynchronously create order
        return orderService.createOrder(order);
    }
}
