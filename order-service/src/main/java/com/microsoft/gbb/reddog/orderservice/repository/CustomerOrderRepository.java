package com.microsoft.gbb.reddog.orderservice.repository;

import com.microsoft.gbb.reddog.orderservice.entity.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {
}