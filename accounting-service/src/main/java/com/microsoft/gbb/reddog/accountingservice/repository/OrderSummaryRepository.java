package com.microsoft.gbb.reddog.accountingservice.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.microsoft.gbb.reddog.accountingservice.dto.ChartKeyValue;
import com.microsoft.gbb.reddog.accountingservice.dto.OrderSummaryDto;
import com.microsoft.gbb.reddog.accountingservice.dto.OrdersTimeSeries;
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

    @Query(value = "SELECT * FROM c where c.orderCompletedDate = null OFFSET 1 LIMIT 50")
    List<OrderSummaryDto> findAllInflightOrders();

    @Query(value = "SELECT * FROM c where c.orderCompletedDate != null OFFSET 1 LIMIT 50")
    List<OrderSummaryDto> findAllCompletedOrders();

    @Query(value = "SELECT * FROM c WHERE c.id = @storeId and c.orderCompletedDate = null")
    List<OrderSummaryDto> findAllInflightOrdersForStore(@Param("storeId") String storeId);

    @Query(value = "SELECT count(1) as total," +
            "c.createdAt[0] as year,c.createdAt[1] as month, c.createdAt[2] as day," +
            "c.createdAt[3] as hour, c.createdAt[4] as minute " +
            "FROM c " +
            "where c.storeId = \" @storeId\" and c.orderCompletedDate = null" +
            "and ROUND(((GetCurrentTicks()/10000) - c.orderDateInstant) / 60 / 1000) < @timespan" +
            "group by c.createdAt")
    OrdersTimeSeries getOrderCountForThePastTimeSpan(@Param("period") String period,
                                                     @Param("timeSpan") String timeSpan,
                                                     @Param("storeId") String storeId);

    @Query(value = "SELECT count(1) as total," +
            "c.createdAt[0] as year,c.createdAt[1] as month, c.createdAt[2] as day," +
            "c.createdAt[3] as hour, c.createdAt[4] as minute " +
            "FROM c " +
            "where c.storeId = \" @storeId\" and c.orderCompletedDate = null" +
            "and @timeStart < ROUND(((GetCurrentTicks()/10000) - c.orderDateInstant) / 60 / 1000) < @timeEnd" +
            "group by c.createdAt")
    OrdersTimeSeries getOrderCountWithinTimeInterval(@Param("period") String period,
                                           @Param("timeStart") String timeStart,
                                           @Param("timeEnd") String timeEnd,
                                           @Param("storeId") String storeId);

    @Query(value = "SELECT count(1) as pv, SUBSTRING(TimestampToDateTime(ROUND((c.orderDateInstant) / 60 / 100000) * 60 * 100000),5,8) as label \n" +
            "FROM c \n" +
            "group by ROUND((c.orderDateInstant) / 60 / 100000)")
    List<ChartKeyValue<Long>> getOrderCountByDay(String storeId);
}