package com.microsoft.gbb.rasa.accountingservice.mapper;

import com.microsoft.gbb.rasa.accountingservice.dto.ProductDto;
import com.microsoft.gbb.rasa.accountingservice.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
}