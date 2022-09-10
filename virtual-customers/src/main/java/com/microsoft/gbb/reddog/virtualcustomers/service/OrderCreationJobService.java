package com.microsoft.gbb.reddog.virtualcustomers.service;

import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.spring.annotations.Recurring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * The type Order service.
 */
@Slf4j
@Component
public class OrderCreationJobService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreationJobService.class);
    public static final String ORDER_SVC_APP_ID = "orderservice";
    public static final String ORDER_SVC_URL = "http://localhost:3500/v1.0/invoke/";

    public OrderCreationJobService() {
    }

    @Recurring(id = "create-orders", cron = "*/5 * * * *")
    @Job(name = "Virtual Customers")
    public void execute() {
        LOGGER.info("Creating orders");
        // TODO: add configuration similar to .NET version
        // TODO: Use Webclient to invoke rest call to order service
        WebClient webClient = WebClient.create(ORDER_SVC_URL + ORDER_SVC_APP_ID + "/method/createorder");
    }
}
