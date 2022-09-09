package com.microsoft.gbb.rasa.orderservice.repositories;

import com.microsoft.gbb.rasa.orderservice.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ProductRepository extends CrudRepository<Product, Long> {
    ArrayList<Product> findAll();
}