package com.microsoft.gbb.reddog.virtualworker.service;

import com.microsoft.gbb.reddog.virtualworker.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.virtualworker.model.OrderSummary;
import io.leego.banana.Ansi;
import io.leego.banana.BananaUtils;
import io.leego.banana.Font;
import io.leego.banana.Layout;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Order service.
 */
@Slf4j
@Component
public class VirtualWorkerService {
    @Value("${data.MAKELINE_SVC_URL}")
    private String makelineServiceUrl;
    private static final WebClient webClient = WebClient.create();

    public VirtualWorkerService() {

    }

    public void checkOrders(String storeId) {
        log.info("Checking orders for store: {}", storeId);
        List<OrderSummaryDto> orders = getOrders(storeId);
        orders.forEach(this::completeOrder);
        log.info("Check Orders");

    }

    public List<OrderSummaryDto> getOrders(String storeId) {
        return webClient.get()
                .uri(makelineServiceUrl + "orders/" + storeId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OrderSummaryDto>>() {})
                .block();
    }

    public void completeOrder(OrderSummaryDto orderSummary) {
        String logMessage = MessageFormat.format("\nCompleting order for {0} @ store {1}\n",
                orderSummary.getFirstName(),
                orderSummary.getStoreId());
        log.info(BananaUtils.bananansi(logMessage, Font.THREE_POINT, Ansi.PURPLE));
        // Invoke makeline service to complete order
        webClient.delete()
                .uri(makelineServiceUrl + "orders/" + orderSummary.getStoreId() + "/" + orderSummary.getOrderId())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public List<OrderSummaryDto> completeOrders(String[] orderIds) {
        List<OrderSummaryDto> orders = new ArrayList<>();
        for (String orderId : orderIds) {
            OrderSummaryDto order = webClient.delete()
                    .uri(makelineServiceUrl + "orders/" + orderId)
                    .retrieve()
                    .bodyToMono(OrderSummaryDto.class)
                    .block();
            orders.add(order);
        }
        return orders;
    }
}
