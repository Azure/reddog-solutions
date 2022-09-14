package com.microsoft.gbb.rasa.accountingservice.service;

import com.microsoft.gbb.rasa.accountingservice.dto.OrderItemSummaryDto;
import com.microsoft.gbb.rasa.accountingservice.dto.OrderSummaryDto;
import com.microsoft.gbb.rasa.accountingservice.entities.CustomerOrder;
import com.microsoft.gbb.rasa.accountingservice.entities.Product;
import com.microsoft.gbb.rasa.accountingservice.exception.ProductsNotFoundException;
import com.microsoft.gbb.rasa.accountingservice.messaging.TopicProducer;
import com.microsoft.gbb.rasa.accountingservice.repositories.CustomerOrderRepository;
import com.microsoft.gbb.rasa.accountingservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Handle accounting related queries.
 */
@Slf4j
@Service
@Transactional
public class AccountingService {
    private final TopicProducer topicProducer;
    private final ProductRepository productRepository;

    public AccountingService(TopicProducer topicProducer,
                             ProductRepository productRepository,
                             CustomerOrderRepository customerOrderRepository) {
        this.topicProducer = topicProducer;
        this.productRepository = productRepository;
    }

    // TODO: implement the following methods with JPA queries
    public void UpdateMetrics(OrderSummaryDto orderSummary) {}
    public void markOrderComplete(OrderSummaryDto orderSummary) {}
    public void getOrderCountOverTime(OrderSummaryDto orderSummary) {}
    public List<String> getUniqueStores() { return null; }
    public void getCorpSalesAndProfitPerStore() { }
    public void getCorpSalesAndProfitTotal() { }
    public void getOrderMetrics() { }
    public void getOrdersByMinute() { }
    public void getOrdersByHour() { }
    public void getOrdersByDay() { }

}
