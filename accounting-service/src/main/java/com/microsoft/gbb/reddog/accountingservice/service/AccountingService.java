package com.microsoft.gbb.reddog.accountingservice.service;

import com.microsoft.gbb.reddog.accountingservice.dto.ChartKeyValue;
import com.microsoft.gbb.reddog.accountingservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.accountingservice.dto.OrdersTimeSeries;
import com.microsoft.gbb.reddog.accountingservice.repository.OrderSummaryRepository;
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

    public List<OrderSummaryDto> findAllCompletedOrders() {
        log.info("Finding all completed orders");
        return orderSummaryRepository.findAllCompletedOrders();
    }

    public OrdersTimeSeries getOrderCountOverTime(String period, String timeSpan, String storeId) {
        log.info("Getting order count for the prior {} {}", timeSpan, period);
        return orderSummaryRepository.getOrderCountForThePastTimeSpan(period, timeSpan, storeId);
    }

    public OrdersTimeSeries getOrderCountWithinTimeInterval(String period,
                                                            String timeStart,
                                                            String timeEnd,
                                                            String storeId) {
        log.info("Getting order count between {} and {}", timeStart, timeEnd);
        return orderSummaryRepository.getOrderCountWithinTimeInterval(period, timeStart, timeEnd, storeId);
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


    public List<ChartKeyValue<Long>> getOrderCountByDay(String storeId) {
        log.info("Getting order count by day");
        return orderSummaryRepository.getOrderCountByDay(storeId);
    }
}
