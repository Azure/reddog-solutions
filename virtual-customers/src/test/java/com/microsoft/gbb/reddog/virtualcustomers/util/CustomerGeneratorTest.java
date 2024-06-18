//package com.microsoft.gbb.reddog.virtualcustomers.util;
//
//import com.microsoft.gbb.reddog.virtualcustomers.model.CustomerOrderItem;
//import com.microsoft.gbb.reddog.virtualcustomers.model.Product;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class CustomerGeneratorTest {
//    @Autowired
//    private CustomerGenerator customerGenerator;
//
//    private static final int MAX_UNIQUE_ITEMS_PER_ORDER = 10;
//
//    @Test
//    @DisplayName(
//            "Should return a list of order items with the size equal to the number of unique items per order")
//    void generateOrderItemsWhenTotalNumOfProductsIsLessThanMaxUniqueItemsPerOrder() {
//        int totalNumOfProducts = 10;
//        List<Product> products = new ArrayList<>();
//        for (int i = 0; i < totalNumOfProducts; i++) {
//            products.add(Product.builder().productId(i).build());
//        }
//
//        List<CustomerOrderItem> orderItems = customerGenerator.generateOrderItems(products);
//
//        assertTrue(totalNumOfProducts > orderItems.size());
//    }
//
//    @Test
//    @DisplayName(
//            "Should return a number between 1 and totalnumofproducts when totalnumofproducts is less than or equal to max_unique_items_per_order")
//    void generateNumOrderItemsWhenTotalNumOfProductsIsLessThanOrEqualToMaxUniqueItemsPerOrder() {
//        int totalNumOfProducts = 10;
//        int numOrderItems = customerGenerator.generateNumOrderItems(totalNumOfProducts);
//        assertTrue(numOrderItems >= 1 && numOrderItems <= totalNumOfProducts);
//    }
//
//    @Test
//    @DisplayName(
//            "Should return a number between 1 and max_unique_items_per_order when totalnumofproducts is greater than max_unique_items_per_order")
//    void generateNumOrderItemsWhenTotalNumOfProductsIsGreaterThanMaxUniqueItemsPerOrder() {
//        int totalNumOfProducts = 100;
//        int numOrderItems = customerGenerator.generateNumOrderItems(totalNumOfProducts);
//        assertTrue(
//                numOrderItems >= 1
//                        && numOrderItems <= MAX_UNIQUE_ITEMS_PER_ORDER);
//    }
//
//    @Test
//    @DisplayName("Should return a number between the current time and long.max_value")
//    void generateLoyaltyIdShouldReturnANumberBetweenTheCurrentTimeAndLongMaxValue() {
//        long loyaltyId = customerGenerator.generateLoyaltyId();
//        assertTrue(loyaltyId > System.currentTimeMillis());
//        assertTrue(loyaltyId < Long.MAX_VALUE);
//    }
//
//    @Test
//    @DisplayName("Should return a random last name")
//    void generateLastNameShouldReturnRandomLastName() {
//        String lastName = customerGenerator.generateLastName();
//        assertNotNull(lastName);
//        assertTrue(lastName.length() > 0);
//    }
//
//    @Test
//    @DisplayName("Should return a first name")
//    void generateFirstNameShouldReturnAFirstName() {
//        String firstName = customerGenerator.generateFirstName();
//        assertNotNull(firstName);
//        assertTrue(firstName.length() > 0);
//    }
//}