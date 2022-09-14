package com.microsoft.gbb.reddog.makelineservice.controller;

import com.microsoft.gbb.reddog.makelineservice.exception.SaveOrderException;
import com.microsoft.gbb.reddog.makelineservice.model.OrderSummary;
import com.microsoft.gbb.reddog.makelineservice.service.MakelineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
public class MakelineController {

    private final MakelineService makelineService;

    public MakelineController(MakelineService makelineService) {
        this.makelineService = makelineService;
    }

    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> addOrderToMakeLine(@RequestBody OrderSummary orderSummary) {
        if (null == orderSummary) {
            throw new SaveOrderException("OrderSummary is empty");
        }
        return ResponseEntity.ok(makelineService.addOrderToMakeLine(orderSummary));
    }

    @GetMapping(value = "/orders/{storeId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ArrayList<OrderSummary>> getOrders(@PathVariable String storeId) {
        if (null == storeId) {
            throw new SaveOrderException("Store ID is empty");
        }
        return ResponseEntity.ok(makelineService.getOrders(storeId));
    }

    @DeleteMapping(value = "/orders/{storeId}/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> completeOrder(@PathVariable String storeId, @PathVariable String orderId) {
        if (null == storeId || null == orderId) {
            throw new SaveOrderException("Store ID or Order ID is empty");
        }
        return ResponseEntity.ok(makelineService.completeOrder(storeId, UUID.fromString(orderId)));
    }
}
