package com.microsoft.gbb.reddog.virtualcustomers.util;

import com.github.javafaker.Faker;

import com.microsoft.gbb.reddog.virtualcustomers.model.CustomerOrderItem;
import com.microsoft.gbb.reddog.virtualcustomers.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerGenerator {
    private static final Faker faker = new Faker();
    @Value("${data.MAX_UNIQUE_ITEMS_PER_ORDER}")
    private static int MAX_UNIQUE_ITEMS_PER_ORDER;

    public String generateFirstName() {
        return faker.name().firstName();
    }

    public String generateLastName() {
        return faker.name().lastName();
    }

    public String generateLoyaltyId() {
        return faker.number().digits(10);
    }

    public int generateNumOrderItems(int totalNumOfProducts) {
        return faker.number().numberBetween(1, Math.min(totalNumOfProducts, MAX_UNIQUE_ITEMS_PER_ORDER));
    }

    public String generateStoreId() {
        return faker.number().digits(10);
    }

    public String generateProductId() {
        return faker.number().digits(10);
    }

    public String generateCategory() {
        return faker.commerce().department();
    }

    public String generateProductName() {
        return faker.commerce().productName();
    }

    public String generateDescription() {
        return faker.commerce().material();
    }

    public String generateImageUrl() {
        return faker.internet().url();
    }

    public double generateUnitPrice() {
        return faker.number().randomDouble(2, 1, 100);
    }

    public double generateUnitCost() {
        return faker.number().randomDouble(2, 1, 100);
    }


    public List<CustomerOrderItem> generateOrderItems(List<Product> products) {
        int numOrderItems = generateNumOrderItems(products.size());
        List<CustomerOrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < numOrderItems; i++) {
            Product product = products.get(i);
            orderItems.add(CustomerOrderItem.builder()
                    .productId(product.getProductId())
                    .quantity(faker.number().numberBetween(1, 10))
                    .build());
        }
        return orderItems;
    }

}

