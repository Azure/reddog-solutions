package com.microsoft.gbb.reddog.orderservice.mapper;

import com.microsoft.gbb.reddog.orderservice.dto.ProductDto;
import com.microsoft.gbb.reddog.orderservice.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
}