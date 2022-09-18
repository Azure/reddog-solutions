package com.microsoft.gbb.rasa.makelineservice.controller;

import com.microsoft.gbb.rasa.makelineservice.dto.OrderSummaryDto;
import com.microsoft.gbb.rasa.makelineservice.exception.SaveOrderException;
import com.microsoft.gbb.rasa.makelineservice.service.MakelineService;
import com.microsoft.gbb.rasa.makelineservice.model.OrderSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@Slf4j
public class MakelineController {

    private final MakelineService makelineService;

    public MakelineController(MakelineService makelineService) {
        this.makelineService = makelineService;
    }

    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> addOrderToMakeLine(@RequestBody OrderSummaryDto orderSummaryDto) {
        if (null == orderSummaryDto) {
            throw new SaveOrderException("OrderSummary is empty");
        }
        return ResponseEntity.ok(makelineService.addOrderToMakeLine(orderSummaryDto));
    }

    // TODO: Refactor with Avro schema in EH Schema Registry
    // @KafkaListener(topics = "reddog.ordercompleted" , groupId = "${'${data.topic.group}'")
    public void addOrderToMakeLineAsync(OrderSummaryDto orderSummaryDto) {
        log.info("Received order to make line: " + orderSummaryDto);
        if (null == orderSummaryDto) {
            throw new SaveOrderException("OrderSummary is empty");
        }
        makelineService.addOrderToMakeLine(orderSummaryDto);
    }

    @GetMapping(value = "/orders/{storeId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ArrayList<OrderSummary>> getOrders(@PathVariable String storeId) {
        if (null == storeId) {
            throw new SaveOrderException("Store ID is empty");
        }
        return ResponseEntity.ok(makelineService.getOrders(storeId));
    }

    @DeleteMapping(value = "/orders/{storeId}/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> completeOrder(@PathVariable String storeId, @PathVariable String orderId) {
        if (null == storeId || null == orderId) {
            throw new SaveOrderException("Store ID or Order ID is empty");
        }
        return ResponseEntity.ok(makelineService.completeOrder(storeId, UUID.fromString(orderId)));
    }
}
