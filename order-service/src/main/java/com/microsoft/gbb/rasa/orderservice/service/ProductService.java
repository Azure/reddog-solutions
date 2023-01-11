package com.microsoft.gbb.rasa.orderservice.service;

import com.microsoft.gbb.rasa.orderservice.dto.ProductDto;
import com.microsoft.gbb.rasa.orderservice.mapper.ProductMapper;
import com.microsoft.gbb.rasa.orderservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private static final String PRODUCT_QUEUE = "productimagegen";

    @Autowired
    private JmsTemplate jmsTemplate;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getAllProducts() {
        return productMapper.toDto(productRepository.findAll());
    }

    public List<String> generateProductImages(List<String> productIds) {
        List<String> productImages = new ArrayList<>();
        for (String productId : productIds) {
            log.info("Generating product image for product id: {}", productId);
            jmsTemplate.convertAndSend(PRODUCT_QUEUE, productId);
        }
        return productImages;
    }
}
