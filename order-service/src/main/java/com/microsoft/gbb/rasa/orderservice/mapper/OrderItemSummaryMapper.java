package com.microsoft.gbb.rasa.orderservice.mapper;

import com.microsoft.gbb.rasa.orderservice.dto.OrderItemSummaryDto;
import com.microsoft.gbb.rasa.orderservice.entities.OrderItemSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemSummaryMapper extends EntityMapper<OrderItemSummaryDto, OrderItemSummary> {
}