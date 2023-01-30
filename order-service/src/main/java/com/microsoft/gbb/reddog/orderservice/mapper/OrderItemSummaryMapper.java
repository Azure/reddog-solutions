package com.microsoft.gbb.reddog.orderservice.mapper;

import com.microsoft.gbb.reddog.orderservice.dto.OrderItemSummaryDto;
import com.microsoft.gbb.reddog.orderservice.entity.OrderItemSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemSummaryMapper extends EntityMapper<OrderItemSummaryDto, OrderItemSummary> {
}