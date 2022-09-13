package com.microsoft.gbb.rasa.orderservice.service;

import com.microsoft.gbb.rasa.orderservice.dto.OrderItemSummaryDto;
import com.microsoft.gbb.rasa.orderservice.dto.OrderSummaryDto;
import com.microsoft.gbb.rasa.orderservice.entities.CustomerOrder;
import com.microsoft.gbb.rasa.orderservice.entities.OrderItemSummary;
import com.microsoft.gbb.rasa.orderservice.entities.Product;
import com.microsoft.gbb.rasa.orderservice.exception.ProductsNotFoundException;
import com.microsoft.gbb.rasa.orderservice.messaging.TopicProducer;
import com.microsoft.gbb.rasa.orderservice.repositories.CustomerOrderRepository;
import com.microsoft.gbb.rasa.orderservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public OrderSummaryDto createOrder(CustomerOrder order) {
        log.info("Creating order");
        var orderSummary = getOrderSummary(order);
        topicProducer.send(orderSummary);
        return orderSummary;
    }

    public OrderSummaryDto getOrderSummary(CustomerOrder order) {
        // Retrieve all the items
        List<Product> products = Optional.ofNullable(productRepository.findAll()).orElseThrow(() -> {
            log.error("Unable to fetch products");
            return new ProductsNotFoundException("Unable to fetch products");
        });

        // Iterate through the list of ordered items to calculate
        // the total and compile a list of item summaries.
        AtomicReference<Float> orderTotal = new AtomicReference<>(0.0f);
        List<OrderItemSummaryDto> itemSummaries = new ArrayList<OrderItemSummaryDto>();
        order.getCustomerOrderItems().forEach(orderItem -> {
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
                .orderId(UUID.randomUUID())
                .storeId(order.getStoreId())
                .firstName(order.getFirstName())
                .lastName(order.getLastName())
                .loyaltyId(order.getLoyaltyId())
                .orderDate(String.valueOf(new Date()))
                .orderItems(itemSummaries)
                .orderTotal(BigDecimal.valueOf(orderTotal.get()).setScale(2, RoundingMode.HALF_DOWN).doubleValue())
                .build();
    }
}
