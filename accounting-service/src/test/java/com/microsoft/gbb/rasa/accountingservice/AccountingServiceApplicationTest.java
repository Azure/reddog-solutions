package com.microsoft.gbb.rasa.accountingservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountingServiceApplicationTests {

    @Test
    @DisplayName("Should start the application")
    void mainShouldStartTheApplication() {
        AccountingServiceApplication.main(new String[]{});
    }
}