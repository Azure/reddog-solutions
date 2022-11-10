package com.microsoft.gbb.reddog.virtualcustomers.controller;


import com.microsoft.gbb.reddog.virtualcustomers.model.CustomerOrder;
import com.microsoft.gbb.reddog.virtualcustomers.service.OrderCreationJobService;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class VirtualCustomersController {

    private final JobScheduler jobScheduler;
    private final OrderCreationJobService orderCreationJobService;

    public VirtualCustomersController(JobScheduler jobScheduler, OrderCreationJobService orderCreationJobService1) {
        this.jobScheduler = jobScheduler;
        this.orderCreationJobService = orderCreationJobService1;
    }

    @PostMapping(value = "/simulate-orders")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CustomerOrder>> createOrderJob(
            @RequestParam(value = "numOrders", defaultValue = "1") int numOrders,
            @RequestHeader(value="x-origin", required = false) String origin) {
        if (numOrders < 1) {
            return ResponseEntity.badRequest().build();
        }
        log.info("Creating {} orders from {}", numOrders, origin);
        List<CustomerOrder> orders = orderCreationJobService.createOrders(numOrders, origin);
        jobScheduler.enqueue(orderCreationJobService::execute);
        return ResponseEntity.ok(orders);
    }
}
