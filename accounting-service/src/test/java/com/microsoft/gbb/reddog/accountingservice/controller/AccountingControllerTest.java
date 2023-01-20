package com.microsoft.gbb.reddog.accountingservice.controller;

import com.microsoft.gbb.reddog.accountingservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.accountingservice.service.AccountingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountingControllerTest {

    @Mock
    private AccountingService accountingService;

    @InjectMocks
    private AccountingController accountingController;

    @Test
    @DisplayName("Should return 200 ok when there are no in flight orders")
    void getAllInFlightOrdersWhenThereAreNoInFlightOrdersThenReturn200OK() {
        when(accountingService.findAllInflightOrders()).thenReturn(Collections.emptyList());

        ResponseEntity<List<OrderSummaryDto>> response =
                accountingController.getAllInFlightOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).isEmpty());
    }

    @Test
    @DisplayName("Should return 200 ok when there are in flight orders")
    void getAllInFlightOrdersWhenThereAreInFlightOrdersThenReturn200OK() {
        List<OrderSummaryDto> orderSummaryDtos = new ArrayList<>();
        orderSummaryDtos.add(OrderSummaryDto.builder().build());
        when(accountingService.findAllInflightOrders()).thenReturn(orderSummaryDtos);

        ResponseEntity<List<OrderSummaryDto>> responseEntity =
                accountingController.getAllInFlightOrders();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).size());
    }
}