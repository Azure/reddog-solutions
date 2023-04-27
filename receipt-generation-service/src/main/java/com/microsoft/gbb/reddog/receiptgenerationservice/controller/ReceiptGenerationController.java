package com.microsoft.gbb.reddog.receiptgenerationservice.controller;

import com.microsoft.gbb.reddog.receiptgenerationservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.receiptgenerationservice.exception.ReceiptSaveException;
import com.microsoft.gbb.reddog.receiptgenerationservice.service.ReceiptGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/receipt-generation")
public class ReceiptGenerationController {

    private final ReceiptGenerationService receiptGenerationService;

    public ReceiptGenerationController(ReceiptGenerationService receiptGenerationService) {
        this.receiptGenerationService = receiptGenerationService;
    }

    @PostMapping(value = "/generate")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> generateReceipt(@RequestBody OrderSummaryDto orderSummary) {
        if (null == orderSummary) {
            throw new ReceiptSaveException("Order is null");
        }
        String status = receiptGenerationService.generateReceipt(orderSummary);
        log.info("Successfully generated receipt: " + status);
        return ResponseEntity.ok(status);
    }

    // TODO: Refactor with Avro schema in EH Schema Registry
    @KafkaListener(id="generateReceipt",
            topics = "#{'${spring.kafka.topic.name}'}",
            groupId = "#{'${spring.kafka.topic.group}'}")
    public void generateReceiptAsync(OrderSummaryDto orderSummary) {
        log.info("Received message in topic: " + orderSummary);
        this.generateReceipt(orderSummary);
    }

}
