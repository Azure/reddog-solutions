package com.microsoft.gbb.reddog.orderservice.repository;

import com.microsoft.gbb.reddog.orderservice.entity.OrderItemSummary;
import org.springframework.data.repository.CrudRepository;

public interface CustomerOrderItemRepository extends CrudRepository<OrderItemSummary, Long> {
   }