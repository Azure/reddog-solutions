package com.microsoft.gbb.rasa.accountingservice.repositories;

import com.microsoft.gbb.rasa.accountingservice.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();

}