package com.microsoft.gbb.reddog.loyaltyservice.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("loyalty-service")
public interface LoyaltyServiceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/orders")
    void updateLoyalty();

}