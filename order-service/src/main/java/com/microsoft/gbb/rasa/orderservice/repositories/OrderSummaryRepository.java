package com.microsoft.gbb.rasa.orderservice.repositories;

import com.microsoft.gbb.rasa.orderservice.entities.OrderSummary;
import org.springframework.data.repository.CrudRepository;

public interface OrderSummaryRepository extends CrudRepository<OrderSummary, Long> {
}