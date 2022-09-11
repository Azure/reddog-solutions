package com.microsoft.gbb.rasa.orderservice.service;

import com.microsoft.gbb.rasa.orderservice.entities.CustomerOrder;
import com.microsoft.gbb.rasa.orderservice.entities.OrderItemSummary;
import com.microsoft.gbb.rasa.orderservice.entities.OrderSummary;
import com.microsoft.gbb.rasa.orderservice.entities.Product;
import com.microsoft.gbb.rasa.orderservice.messaging.TopicProducer;
import com.microsoft.gbb.rasa.orderservice.repositories.CustomerOrderRepository;
import com.microsoft.gbb.rasa.orderservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Create new orders for customers.
 */
@Slf4j
@Component
@Qualifier("customerorder")
public class CustomerOrderService implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderService.class);
    private final TopicProducer topicProducer;
    private final ProductRepository productRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderService(TopicProducer topicProducer,
                                ProductRepository productRepository,
                                CustomerOrderRepository customerOrderRepository) {
        this.topicProducer = topicProducer;
        this.productRepository = productRepository;
        this.customerOrderRepository = customerOrderRepository;
    }
    /**
     * Create order for customer.
     *
     * @param order the order
     * @return the string
     */
    public OrderSummary createOrder(CustomerOrder order) {
        LOGGER.info("Creating order");
        topicProducer.send(order.toString());
        var orderSummary = getOrderSummary(order);
        topicProducer.send(orderSummary.toString());
        return orderSummary;
    }

    public OrderSummary getOrderSummary(CustomerOrder order) {
        // Retrieve all the items
        ArrayList<Product> products = productRepository.findAll();

        // Iterate through the list of ordered items to calculate
        // the total and compile a list of item summaries.
        AtomicReference<Float> orderTotal = new AtomicReference<>(0.0f);
        var itemSummaries = new ArrayList<OrderItemSummary>();
        order.getCustomerOrderItems().forEach(orderItem -> {
            Product product = products.stream()
                    .filter((p) -> Objects.equals(p.getProductId(), orderItem.getId()))
                    .findFirst()
                    .orElse(null);
            if (product == null) return;

            orderTotal.updateAndGet(v -> (float) (v + (product.getUnitPrice() * orderItem.getQuantity())));
            itemSummaries.add(OrderItemSummary.builder()
                    .orderSummaryId(orderItem.getId())
                    .productName(product.getProductName())
                    .unitPrice(product.getUnitPrice())
                    .quantity(orderItem.getQuantity())
                    .unitCost(product.getUnitCost())
                    .imageUrl(product.getImageUrl())
                    .build());
        });

        // return initialized order summary
        return OrderSummary.builder()
                .orderId(generateUniqueId())
                .storeId(order.getStoreId())
                .firstName(order.getFirstName())
                .lastName(order.getLastName())
                .loyaltyId(order.getLoyaltyId())
                .orderDate(new Date())
                .orderItemSummaries(itemSummaries)
                .orderTotal(BigDecimal.valueOf(orderTotal.get()).setScale(2, RoundingMode.HALF_DOWN).doubleValue())
                .build();
    }

    private static long generateUniqueId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
