package com.microsoft.gbb.rasa.accountingservice.service;

import com.microsoft.gbb.rasa.accountingservice.dto.ProductDto;
import com.microsoft.gbb.rasa.accountingservice.mapper.ProductMapper;
import com.microsoft.gbb.rasa.accountingservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getAllProducts() {
        return productMapper.toDto(productRepository.findAll());
    }
}
