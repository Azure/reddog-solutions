package com.microsoft.gbb.rasa.makelineservice.mapper;

import com.microsoft.gbb.rasa.makelineservice.dto.OrderItemSummaryDto;
import com.microsoft.gbb.rasa.makelineservice.model.OrderItemSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemSummaryMapper extends EntityMapper<OrderItemSummaryDto, OrderItemSummary> {
}