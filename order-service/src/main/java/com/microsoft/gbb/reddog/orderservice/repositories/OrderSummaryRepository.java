package com.microsoft.gbb.reddog.orderservice.repositories;

import com.microsoft.gbb.reddog.orderservice.entities.OrderSummary;
import org.springframework.data.repository.CrudRepository;

public interface OrderSummaryRepository extends CrudRepository<OrderSummary, Long> {
}