package com.microsoft.gbb.reddog.orderservice.repositories;

import com.microsoft.gbb.reddog.orderservice.entities.OrderItemSummary;
import org.springframework.data.repository.CrudRepository;

public interface CustomerOrderItemRepository extends CrudRepository<OrderItemSummary, Long> {
   }