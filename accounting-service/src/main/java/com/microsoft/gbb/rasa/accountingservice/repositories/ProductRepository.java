package com.microsoft.gbb.rasa.accountingservice.repositories;

import com.microsoft.gbb.rasa.accountingservice.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ProductRepository extends CrudRepository<Product, Long> {
    ArrayList<Product> findAll();
}