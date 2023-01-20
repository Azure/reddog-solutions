package com.microsoft.gbb.reddog.makelineservice.mapper;

import com.microsoft.gbb.reddog.makelineservice.dto.OrderItemSummaryDto;
import com.microsoft.gbb.reddog.makelineservice.model.OrderItemSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemSummaryMapper extends EntityMapper<OrderItemSummaryDto, OrderItemSummary> {
}