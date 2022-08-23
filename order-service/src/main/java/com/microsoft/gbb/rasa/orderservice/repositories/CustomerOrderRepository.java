package com.microsoft.gbb.rasa.orderservice.repositories;

import com.microsoft.gbb.rasa.orderservice.entities.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {
}