package com.microsoft.gbb.reddog.virtualcustomers.controller;

import com.microsoft.gbb.reddog.virtualcustomers.service.OrderCreationJobService;
import org.jobrunr.jobs.lambdas.JobLambda;
import org.jobrunr.scheduling.JobScheduler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VirtualCustomersControllerTest {

    @Mock
    private JobScheduler jobScheduler;

    @Mock
    private OrderCreationJobService orderCreationJobService;

    @InjectMocks
    private VirtualCustomersController virtualCustomersController;

    @Test
    @DisplayName("Should schedule the job when the request is valid")
    void createOrderJobWhenRequestIsValidThenScheduleTheJob() {
        int numOrders = 10;
        ResponseEntity<String> responseEntity =
                virtualCustomersController.createOrderJob(numOrders);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Order creation job scheduled", responseEntity.getBody());
        verify(jobScheduler, times(1)).enqueue((JobLambda) any());
    }
}