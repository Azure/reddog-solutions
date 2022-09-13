package com.microsoft.gbb.rasa.orderservice.mapper;

import com.microsoft.gbb.rasa.orderservice.dto.ProductDto;
import com.microsoft.gbb.rasa.orderservice.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
}