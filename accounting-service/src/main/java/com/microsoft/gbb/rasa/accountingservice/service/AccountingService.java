package com.microsoft.gbb.rasa.accountingservice.service;

import com.microsoft.gbb.rasa.accountingservice.dto.OrderSummaryDto;
import com.microsoft.gbb.rasa.accountingservice.dto.OrdersTimeSeries;
import com.microsoft.gbb.rasa.accountingservice.repositories.OrderSummaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Handle accounting related queries.
 */
@Slf4j
@Service
@Transactional
public class AccountingService {
    private final OrderSummaryRepository orderSummaryRepository;

    public AccountingService(OrderSummaryRepository orderSummaryRepository) {
        this.orderSummaryRepository = orderSummaryRepository;
    }

    public List<OrderSummaryDto> findAllInflightOrders() {
        log.info("Finding all inflight orders");
        return orderSummaryRepository.findAllInflightOrders();
    }

    public OrdersTimeSeries getOrderCountOverTime(String period, String timeSpan, String storeId) {
        log.info("Getting order count over time");
        return orderSummaryRepository.getOrderCountOverTime(period, timeSpan, storeId);
    }

    // TODO: implement the following methods with JPA queries
    public void UpdateMetrics(OrderSummaryDto orderSummary) {}
    public void markOrderComplete(OrderSummaryDto orderSummary) {}
    public List<String> getUniqueStores() { return null; }
    public void getCorpSalesAndProfitPerStore() { }
    public void getCorpSalesAndProfitTotal() { }
    public void getOrderMetrics() { }
    public void getOrdersByMinute() { }
    public void getOrdersByHour() { }
    public void getOrdersByDay() { }


}
