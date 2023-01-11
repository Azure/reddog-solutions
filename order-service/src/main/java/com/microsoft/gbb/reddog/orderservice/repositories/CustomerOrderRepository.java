package com.microsoft.gbb.reddog.orderservice.repositories;

import com.microsoft.gbb.reddog.orderservice.entities.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {
}