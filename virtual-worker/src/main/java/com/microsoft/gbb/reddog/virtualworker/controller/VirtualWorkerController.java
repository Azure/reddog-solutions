package com.microsoft.gbb.reddog.virtualworker.controller;


import com.microsoft.gbb.reddog.virtualworker.service.VirtualWorkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VirtualWorkerController {

    private final VirtualWorkerService orderCreationJobService;

    public VirtualWorkerController(VirtualWorkerService orderCreationJobService1) {
        this.orderCreationJobService = orderCreationJobService1;
    }

    @PostMapping(value = "/orders")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> checkOrders() {
        orderCreationJobService.checkOrders();
        return ResponseEntity.noContent().build();
    }
}
