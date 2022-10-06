package com.microsoft.gbb.rasa.accountingservice.mapper;

import com.microsoft.gbb.rasa.accountingservice.dto.OrderItemSummaryDto;
import com.microsoft.gbb.rasa.accountingservice.model.OrderItemSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemSummaryMapper extends EntityMapper<OrderItemSummaryDto, OrderItemSummary> {
}