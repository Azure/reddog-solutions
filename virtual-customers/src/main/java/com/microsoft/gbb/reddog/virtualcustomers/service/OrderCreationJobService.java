package com.microsoft.gbb.reddog.virtualcustomers.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.microsoft.gbb.reddog.virtualcustomers.model.CustomerOrder;
import com.microsoft.gbb.reddog.virtualcustomers.model.Product;
import com.microsoft.gbb.reddog.virtualcustomers.util.CustomerGenerator;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.spring.annotations.Recurring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Order service.
 */
@Slf4j
@Component
public class OrderCreationJobService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreationJobService.class);

    @Value("${data.ORDER_SVC_URL}")
    private String orderServiceUrl;

    private static String ORDER_SVC_URL;

    @Value("${data.STORE_ID}")
    private String STORE_ID;

    private final CustomerGenerator customerGenerator;

    private static final WebClient webClient = WebClient.create();

    public OrderCreationJobService(CustomerGenerator customerGenerator) {
        this.customerGenerator = customerGenerator;
    }
    @Value("${data.ORDER_SVC_URL}")
    public void setOrderServiceUrlStatic(String orderServiceUrl) {
        OrderCreationJobService.ORDER_SVC_URL = orderServiceUrl + "/";
    }
    @Recurring(id = "create-orders", cron = "#{'${data.CREATE_ORDER_CRON_SCHEDULE}'}")
    @Job(name = "Virtual Customers")
    public void execute() {
        LOGGER.info("Creating orders");
        // TODO: add additional configs similar to .NET
        List<Product> products = getProducts();
        if (products.isEmpty()) {
            LOGGER.info("No products to generate orders for. Exiting.");
            return;
        }
        CustomerOrder customerOrder = createCustomerOrder(products);
        LOGGER.info("Created order: {}", customerOrder);
    }

    private CustomerOrder createCustomerOrder(List<Product> products) {
        CustomerOrder order = CustomerOrder.builder()
                .storeId(STORE_ID)
                .firstName(customerGenerator.generateFirstName())
                .lastName(customerGenerator.generateLastName())
                .loyaltyId(String.valueOf(customerGenerator.generateLoyaltyId()))
                .orderItems(customerGenerator.generateOrderItems(products))
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String orderJson = null;
        try {
            orderJson = mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing order to JSON {}", e);
        }
        log.info("Creating order: {}", orderJson);
        return webClient.post()
                .uri(ORDER_SVC_URL + "order")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(order)
                .retrieve()
                .bodyToMono(CustomerOrder.class)
                .block();
    }

    private static List<Product> getProducts() {
        WebClient.ResponseSpec responseSpec = webClient.get()
                        .uri(ORDER_SVC_URL + "products")
                                .retrieve();
        return Objects.requireNonNull(responseSpec.bodyToFlux(Product.class).collectList().block());
    }
}
