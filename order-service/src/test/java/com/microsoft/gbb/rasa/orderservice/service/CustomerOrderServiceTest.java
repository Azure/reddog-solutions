package com.microsoft.gbb.rasa.orderservice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerOrderServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerOrderRepository customerOrderRepository;

    @InjectMocks
    private CustomerOrderService customerOrderService;

    @Test
    @DisplayName("Should return an order summary with the correct order id")
    void getOrderSummaryShouldReturnAnOrderSummaryWithTheCorrectOrderId() {
        var order =
                CustomerOrder.builder()
                        .customerOrderId(1L)
                        .storeId("1")
                        .firstName("John")
                        .lastName("Doe")
                        .loyaltyId("1")
                        .customerOrderItems(
                                Collections.singletonList(
                                        CustomerOrderItem.builder().id(1L).quantity(1).build()))
                        .build();

        var product =
                Product.builder()
                        .productId(1L)
                        .productName("Product 1")
                        .unitPrice(10.0)
                        .unitCost(5.0)
                        .imageUrl("http://image.com/product1")
                        .build();

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        var orderSummary = customerOrderService.getOrderSummary(order);

        assertEquals(orderSummary.getOrderId(), order.getCustomerOrderId());
    }

    @Test
    @DisplayName("Should return an order summary with the correct store id")
    void getOrderSummaryShouldReturnAnOrderSummaryWithTheCorrectStoreId() {
        var customerOrder = CustomerOrder.builder().storeId("storeId").build();

        var orderSummary = customerOrderService.getOrderSummary(customerOrder);

        assertEquals(customerOrder.getStoreId(), orderSummary.getStoreId());
    }
}