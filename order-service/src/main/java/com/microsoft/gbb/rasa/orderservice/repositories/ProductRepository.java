package com.microsoft.gbb.rasa.orderservice.repositories;

import com.microsoft.gbb.rasa.orderservice.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}