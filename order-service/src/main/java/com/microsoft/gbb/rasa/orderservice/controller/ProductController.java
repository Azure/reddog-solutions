package com.microsoft.gbb.rasa.orderservice.controller;

import com.microsoft.gbb.rasa.orderservice.entities.Product;
import com.microsoft.gbb.rasa.orderservice.service.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    @CrossOrigin(origins = "*")
    public ArrayList<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
