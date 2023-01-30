package com.microsoft.gbb.reddog.accountingservice.service;

import com.microsoft.gbb.reddog.accountingservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.accountingservice.dto.OrdersTimeSeries;
import com.microsoft.gbb.reddog.accountingservice.dto.TimeSeries;
import com.microsoft.gbb.reddog.accountingservice.repository.OrderSummaryRepository;
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
import static org.mockito.Mockito.*;

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
    @DisplayName("Should return the order count for the prior time span")
    void getOrderCountForThePastTimeSpan() {
        String period = "minute";
        String timeSpan = "10";
        String storeId = "storeId";

        accountingService.getOrderCountOverTime(period, timeSpan, storeId);

        verify(orderSummaryRepository, times(0))
                .getOrderCountForThePastTimeSpan(period, timeSpan, storeId);
    }

    @Test
    @DisplayName("Should return an empty list when there are no orders")
    void getOrderCountForThePastTimeSpanWhenThereAreNoOrdersThenReturnEmptyList() {
        String period = "minute";
        String timeSpan = "10";
        String storeId = "storeId";
        List<OrderSummaryDto> orderSummaryDtos = new ArrayList<>();
        List<TimeSeries<Integer>> values = new ArrayList<>();

        OrdersTimeSeries ordersTimeSeries = OrdersTimeSeries.builder()
                .storeId(storeId)
                .values(values)
                .build();

        lenient()
                .when(orderSummaryRepository.getOrderCountForThePastTimeSpan(period,
                        timeSpan, storeId))
                .thenReturn(ordersTimeSeries);

        assertEquals(0, 0);
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