package com.microsoft.gbb.rasa.makelineservice.controller;

import com.microsoft.gbb.rasa.makelineservice.dto.OrderItemSummaryDto;
import com.microsoft.gbb.rasa.makelineservice.dto.OrderSummaryDto;
import com.microsoft.gbb.rasa.makelineservice.exception.SaveOrderException;
import com.microsoft.gbb.rasa.makelineservice.service.MakelineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MakelineControllerTest {

    @Mock
    private MakelineService makelineService;

    @InjectMocks
    private MakelineController makelineController;

    @Test
    @DisplayName("Should throw an exception when the ordersummarydto is null")
    void addOrderToMakeLineWhenOrderSummaryDtoIsNullThenThrowException() {
        assertThrows(SaveOrderException.class, () -> makelineController.addOrderToMakeLine(null));
    }

    @Test
    @DisplayName("Should return a response with status code 200 when the ordersummarydto is not null")
    void addOrderToMakeLineWhenOrderSummaryDtoIsNotNullThenReturnResponseWithStatusCode200() {
        OrderSummaryDto orderSummaryDto = OrderSummaryDto.builder()
                .orderId("1234")
                .storeId("denver")
                .lastName("Smith")
                .firstName("John")
                .orderDate("2020-01-01")
                .orderTotal(100.00)
                .loyaltyId("1234")
                .orderItems(List.of(OrderItemSummaryDto.builder()
                        .productName("product1")
                        .quantity(1)
                        .productId(2)
                        .build()))
                .build();
        when(makelineService.addOrderToMakeLine(orderSummaryDto)).thenReturn("WIP");
        ResponseEntity<String> response = makelineController.addOrderToMakeLine(orderSummaryDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}