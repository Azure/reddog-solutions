package com.microsoft.gbb.reddog.accountingservice.mapper;

import com.microsoft.gbb.reddog.accountingservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.accountingservice.model.OrderSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderSummaryMapper extends EntityMapper<OrderSummaryDto, OrderSummary> {

    @Mapping(ignore = true, target = "orderId")
    OrderSummary toEntity(OrderSummaryDto dto);

    @Mapping(ignore = true, target = "orderId")
    OrderSummaryDto toDto(OrderSummary entity);
}