package com.microsoft.gbb.rasa.orderservice.repositories;

import com.microsoft.gbb.rasa.orderservice.entities.OrderItemSummary;
import org.springframework.data.repository.CrudRepository;

public interface CustomerOrderItemRepository extends CrudRepository<OrderItemSummary, Long> {
   }