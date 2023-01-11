package com.microsoft.gbb.reddog.makelineservice.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.microsoft.gbb.reddog.makelineservice.dto.OrderSummaryDto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface OrderSummaryRepository extends CosmosRepository<OrderSummaryDto, String> {
    // Query all orders
    @Query(value = "SELECT * FROM c")
    List<OrderSummaryDto> getAllOrders();

    // Query for equality using ==
    @Query(value = "SELECT * FROM c WHERE c.id = @storeId")
    ArrayList<OrderSummaryDto> getOrdersForStore(@Param("storeId") String storeId);

    List<OrderSummaryDto> findAllByStoreId(String storeId);

    // find order by order id and store id
    OrderSummaryDto findByOrderIdAndStoreId(String orderId, String storeId);

    @Query(value = "SELECT * FROM c WHERE c.orderId = @orderId")
    List<OrderSummaryDto> findByOrderId(String orderId);
}
