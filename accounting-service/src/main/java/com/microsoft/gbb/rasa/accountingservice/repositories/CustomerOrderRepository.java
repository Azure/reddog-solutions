package com.microsoft.gbb.rasa.accountingservice.repositories;

import com.microsoft.gbb.rasa.accountingservice.entities.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {
}