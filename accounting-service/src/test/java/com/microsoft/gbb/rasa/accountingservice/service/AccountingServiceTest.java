package com.microsoft.gbb.rasa.accountingservice.service;

import com.microsoft.gbb.rasa.accountingservice.dto.OrderSummaryDto;
import com.microsoft.gbb.rasa.accountingservice.repositories.OrderSummaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountingServiceTest {
    @Mock
    private OrderSummaryRepository orderSummaryRepository;

    @InjectMocks
    private AccountingService accountingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("Should return all inflight orders")
    void findAllInflightOrdersShouldReturnAllInflightOrders() {
        List<OrderSummaryDto> orderSummaryDtos = new ArrayList<>();
        lenient().when(orderSummaryRepository.findAllInflightOrders()).thenReturn(orderSummaryDtos);
        List<OrderSummaryDto> result = accountingService.findAllInflightOrders();
        assertEquals(orderSummaryDtos, result);
    }
}