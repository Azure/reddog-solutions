package com.microsoft.gbb.reddog.orderservice.repository;

import com.microsoft.gbb.reddog.orderservice.entity.OrderSummary;
import org.springframework.data.repository.CrudRepository;

public interface OrderSummaryRepository extends CrudRepository<OrderSummary, Long> {
}