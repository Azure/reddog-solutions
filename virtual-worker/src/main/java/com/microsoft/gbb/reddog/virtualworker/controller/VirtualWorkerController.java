package com.microsoft.gbb.reddog.virtualworker.controller;


import com.microsoft.gbb.reddog.virtualworker.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.virtualworker.service.VirtualWorkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VirtualWorkerController {

    private final VirtualWorkerService orderCreationJobService;

    public VirtualWorkerController(VirtualWorkerService orderCreationJobService1) {
        this.orderCreationJobService = orderCreationJobService1;
    }

    @PostMapping(value = "/orders/{storeId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> checkOrders(@PathVariable(value="storeId")  String storeId) {
        orderCreationJobService.checkOrders(storeId);
        return ResponseEntity.noContent().build();
    }

    // Complete orders for given array of order ids
    @PostMapping(value = "/orders/complete")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<OrderSummaryDto>> completeOrders(@RequestBody String[] orderIds) {
        return ResponseEntity.ok(orderCreationJobService.completeOrders(orderIds));
    }
}
