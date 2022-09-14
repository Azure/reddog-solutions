package com.microsoft.gbb.rasa.accountingservice.repositories;

import com.microsoft.gbb.rasa.accountingservice.entities.CustomerOrderItem;
import org.springframework.data.repository.CrudRepository;

public interface CustomerOrderItemRepository extends CrudRepository<CustomerOrderItem, Long> {
   }