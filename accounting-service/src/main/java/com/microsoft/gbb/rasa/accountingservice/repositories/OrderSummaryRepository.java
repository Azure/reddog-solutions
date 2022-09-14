package com.microsoft.gbb.rasa.accountingservice.repositories;

import com.microsoft.gbb.rasa.accountingservice.entities.OrderSummary;
import org.springframework.data.repository.CrudRepository;

public interface OrderSummaryRepository extends CrudRepository<OrderSummary, Long> {
}