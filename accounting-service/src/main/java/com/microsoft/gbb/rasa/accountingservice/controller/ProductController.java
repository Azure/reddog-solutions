package com.microsoft.gbb.rasa.accountingservice.controller;

import com.microsoft.gbb.rasa.accountingservice.dto.ProductDto;
import com.microsoft.gbb.rasa.accountingservice.exception.ProductsNotFoundException;
import com.microsoft.gbb.rasa.accountingservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = Optional.ofNullable(productService.getAllProducts()).orElseThrow(() -> {
            log.error("Unable to fetch products");
            return new ProductsNotFoundException("Unable to fetch products");
        });
        return ResponseEntity.ok(products);
    }
}
