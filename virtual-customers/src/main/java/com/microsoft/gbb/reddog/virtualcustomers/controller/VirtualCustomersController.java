package com.microsoft.gbb.reddog.virtualcustomers.controller;


import com.microsoft.gbb.reddog.virtualcustomers.service.OrderCreationJobService;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.web.bind.annotation.*;

@RestController
public class VirtualCustomersController {

    private final JobScheduler jobScheduler;
    private final OrderCreationJobService orderCreationJobService;

    public VirtualCustomersController(JobScheduler jobScheduler, OrderCreationJobService orderCreationJobService1) {
        this.jobScheduler = jobScheduler;
        this.orderCreationJobService = orderCreationJobService1;
    }

    @GetMapping(value = "/simulate-orders")
    @CrossOrigin(origins = "*")
    public String createOrderJob(@RequestParam(value = "numOrders", defaultValue = "10") int numOrders) {
        jobScheduler.enqueue(orderCreationJobService::execute);
        return "Job started";
    }
}
