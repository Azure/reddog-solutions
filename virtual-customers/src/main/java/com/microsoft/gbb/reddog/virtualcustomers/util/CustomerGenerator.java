package com.microsoft.gbb.reddog.virtualcustomers.util;

import net.datafaker.Faker;

import com.microsoft.gbb.reddog.virtualcustomers.model.CustomerOrderItem;
import com.microsoft.gbb.reddog.virtualcustomers.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerGenerator {
    private static final Faker faker = new Faker();
    public static final int TOTAL_NUM_OF_PRODUCTS_IN_DB = 52;
    @Value("${data.MAX_UNIQUE_ITEMS_PER_ORDER}")
    private int MAX_UNIQUE_ITEMS_PER_ORDER;

    public String generateFirstName() {
        return faker.name().firstName();
    }

    public String generateLastName() {
        return faker.name().lastName();
    }

    public long generateLoyaltyId() {
        return faker.number().numberBetween(System.currentTimeMillis(), Long.MAX_VALUE);
    }

    public int generateNumOrderItems(int totalNumOfProducts) {
        return faker.number().numberBetween(1, Math.min(totalNumOfProducts, MAX_UNIQUE_ITEMS_PER_ORDER));
    }

    public String generateLatitude() {
        return String.valueOf(faker.address().latitude());
    }

    public String generateLongitude() {
        return String.valueOf(faker.address().longitude());
    }

    public String generateStoreId() {
        return faker.gameOfThrones().city();
    }

    public List<CustomerOrderItem> generateOrderItems(List<Product> products) {
        int numOrderItems = generateNumOrderItems(products.size());
        List<CustomerOrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < numOrderItems; i++) {
            Product product = products.get(i);
            orderItems.add(CustomerOrderItem.builder()
                    .productId(faker.number().numberBetween(1, TOTAL_NUM_OF_PRODUCTS_IN_DB))
                    .quantity(faker.number().numberBetween(1, 10))
                    .build());
        }
        return orderItems;
    }

}

