package com.microsoft.gbb.reddog.orderservice.repository;

import com.microsoft.gbb.reddog.orderservice.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ProductRepository extends CrudRepository<Product, Long> {
    ArrayList<Product> findAll();
}