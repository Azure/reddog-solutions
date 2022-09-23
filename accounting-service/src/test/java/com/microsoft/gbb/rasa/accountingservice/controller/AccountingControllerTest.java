package com.microsoft.gbb.rasa.accountingservice.controller;

import com.microsoft.gbb.rasa.accountingservice.entities.CustomerOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class AccountingControllerTest {

    @Autowired
    private AccountingController accountingController;

    @BeforeEach
    void setUp() {
//        CustomerOrder order = CustomerOrder.builder()
//                .build()
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void order() {
    }
}