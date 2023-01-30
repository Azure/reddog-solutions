package com.microsoft.gbb.reddog.orderservice.service;

import com.microsoft.gbb.reddog.orderservice.dto.CustomerOrderDto;
import com.microsoft.gbb.reddog.orderservice.dto.OrderItemSummaryDto;
import com.microsoft.gbb.reddog.orderservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.orderservice.entity.Product;
import com.microsoft.gbb.reddog.orderservice.exception.ProductsNotFoundException;
import com.microsoft.gbb.reddog.orderservice.messaging.TopicProducer;
import com.microsoft.gbb.reddog.orderservice.repository.CustomerOrderRepository;
import com.microsoft.gbb.reddog.orderservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Create new orders for customers.
 */
@Slf4j
@Service
@Transactional
@Qualifier("customerorder")
public class CustomerOrderService implements OrderService {
    private final TopicProducer topicProducer;
    private final ProductRepository productRepository;

    public CustomerOrderService(TopicProducer topicProducer,
                                ProductRepository productRepository,
                                CustomerOrderRepository customerOrderRepository) {
        this.topicProducer = topicProducer;
        this.productRepository = productRepository;
    }
    /**
     * Create order for customer.
     *
     * @param order the order
     * @return the string
     */
    public OrderSummaryDto createOrder(CustomerOrderDto order) {
        log.info("Creating order");
        var orderSummary = getOrderSummary(order);
        topicProducer.send(orderSummary);
        return orderSummary;
    }

    public OrderSummaryDto getOrderSummary(CustomerOrderDto order) {
        log.info("Creating order summary with order: {}", order.toString());
        // Retrieve all the items
        List<Product> products = Optional.ofNullable(productRepository.findAll()).orElseThrow(() -> {
            log.error("Unable to fetch products");
            return new ProductsNotFoundException("Unable to fetch products");
        });

        // Iterate through the list of ordered items to calculate
        // the total and compile a list of item summaries.
        AtomicReference<Float> orderTotal = new AtomicReference<>(0.0f);
        List<OrderItemSummaryDto> itemSummaries = new ArrayList<OrderItemSummaryDto>();
        order.getOrderItems().forEach(orderItem -> {
            Product product = products.stream()
                    .filter((p) -> Objects.equals(p.getProductId(), orderItem.getId()))
                    .findFirst()
                    .orElse(null);
            if (product == null) return;

            orderTotal.updateAndGet(v -> (float) (v + (product.getUnitPrice() * orderItem.getQuantity())));
            itemSummaries.add(OrderItemSummaryDto.builder()
                    .productName(product.getProductName())
                    .unitPrice(product.getUnitPrice())
                    .quantity(orderItem.getQuantity())
                    .unitCost(product.getUnitCost())
                    .imageUrl(product.getImageUrl())
                    .build());
        });

        // return initialized order summary
        return OrderSummaryDto.builder()
                .orderId(UUID.randomUUID().toString())
                .storeId(order.getStoreId())
                .firstName(order.getFirstName())
                .lastName(order.getLastName())
                .loyaltyId(order.getLoyaltyId())
                .orderDate(LocalDateTime.now())
                .orderDateInstant(getOrderDateInstant())
                .orderItems(itemSummaries)
                .orderTotal(BigDecimal.valueOf(orderTotal.get()).setScale(2, RoundingMode.HALF_DOWN).doubleValue())
                .build();
    }

    private static long getOrderDateInstant() {
        Clock clock = Clock.systemDefaultZone();
        Instant instant = clock.instant();
        return instant.toEpochMilli();
    }
}
