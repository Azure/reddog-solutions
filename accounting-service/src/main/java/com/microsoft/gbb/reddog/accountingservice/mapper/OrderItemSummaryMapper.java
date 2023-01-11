package com.microsoft.gbb.reddog.accountingservice.mapper;

import com.microsoft.gbb.reddog.accountingservice.dto.OrderItemSummaryDto;
import com.microsoft.gbb.reddog.accountingservice.model.OrderItemSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemSummaryMapper extends EntityMapper<OrderItemSummaryDto, OrderItemSummary> {
}