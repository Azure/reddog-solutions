package com.microsoft.gbb.reddog.virtualcustomers.client;

import com.microsoft.gbb.reddog.virtualcustomers.dto.CustomerOrderDto;
import com.microsoft.gbb.reddog.virtualcustomers.dto.OrderSummaryDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "order-service")
public interface OrderServiceClient {
    
    @PostMapping(value = "/order"
                    , consumes = "application/json"
                    , produces = "application/json")
    @CrossOrigin(origins = "*")
    ResponseEntity<OrderSummaryDto> order(@RequestBody @Valid CustomerOrderDto order);
}
