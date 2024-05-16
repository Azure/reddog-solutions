package com.microsoft.gbb.reddog.loyaltyservice.controller;

import com.microsoft.gbb.reddog.loyaltyservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.loyaltyservice.exception.LoyaltySaveException;
import com.microsoft.gbb.reddog.loyaltyservice.model.LoyaltySummary;
import com.microsoft.gbb.reddog.loyaltyservice.service.LoyaltyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/loyalty")
public class LoyaltyController {

    private final LoyaltyService loyaltyService;

    public LoyaltyController(LoyaltyService loyaltyService) {
        this.loyaltyService = loyaltyService;
    }


    @PostMapping(value = "/update")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public ResponseEntity<LoyaltySummary> updateLoyalty(@RequestBody OrderSummaryDto orderSummaryDto) {
        if (null == orderSummaryDto) {
            throw new LoyaltySaveException("OrderSummary is empty");
        }
        return ResponseEntity.ok(loyaltyService.updateLoyalty(orderSummaryDto));
    }

    // TODO: Refactor with Avro schema in EH Schema Registry
    @KafkaListener(id="updateloyalty",
            topics = "#{'${spring.kafka.topic.name}'}",
            groupId = "#{'${spring.kafka.topic.group}'}")
    public void updateLoyaltyAsync(OrderSummaryDto orderSummaryDto) {
        log.info("Received Message in group loyalty-service: " + orderSummaryDto);
        this.updateLoyalty(orderSummaryDto);
    }

}
