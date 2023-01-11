package com.microsoft.gbb.reddog.orderservice.controller;

import com.microsoft.gbb.reddog.orderservice.dto.ProductDto;
import com.microsoft.gbb.reddog.orderservice.exception.ProductsNotFoundException;
import com.microsoft.gbb.reddog.orderservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/product-images")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<String>> generateProductImagesFromDiffusion(@RequestBody List<String> productIds) {
        List<String> productImages = Optional.ofNullable(productService.generateProductImages(productIds)).orElseThrow(() -> {
            log.error("Unable to fetch product images");
            return new ProductsNotFoundException("Unable to fetch product images");
        });
        return ResponseEntity.ok(productImages);
    }
}
