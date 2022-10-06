package com.microsoft.gbb.rasa.accountingservice.repositories;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.microsoft.gbb.rasa.accountingservice.dto.OrderSummaryDto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderSummaryRepository extends CosmosRepository<OrderSummaryDto, String> {
    // Query all orders
    @Query(value = "SELECT * FROM c")
    List<OrderSummaryDto> findAllOrders();

    // Query for equality using ==
    @Query(value = "SELECT * FROM c WHERE c.id = @storeId")
    List<OrderSummaryDto> findAllOrdersForStore(@Param("storeId") String storeId);

    @Query(value = "SELECT * FROM c where c.orderCompletedDate = null")
    List<OrderSummaryDto> findAllInflightOrders();

    @Query(value = "SELECT * FROM c WHERE c.id = @storeId and c.orderCompletedDate = null")
    List<OrderSummaryDto> findAllInflightOrdersForStore(@Param("storeId") String storeId);
}