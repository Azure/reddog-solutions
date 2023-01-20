/*
package com.microsoft.gbb.reddog.makelineservice.repository;

import dto.com.microsoft.gbb.reddog.makelineservice.OrderSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSummaryRedisRepository {
    public static final String KEY = "ordersummaries";
    private final ReactiveRedisOperations<String, List<OrderSummaryDto>> reactiveRedisOperations;

    public Flux<List<OrderSummaryDto>> findAll(){
        return this.reactiveRedisOperations.opsForList().range(KEY, 0, -1);
    }

    public Mono<Long> save(List<OrderSummaryDto> orderSummaries){
        return this.reactiveRedisOperations.opsForList().rightPush(KEY, orderSummaries);
    }

    public Mono<List<OrderSummaryDto>> findById(String id) {
        return this.findAll().filter(p -> p.get(Integer.parseInt(id)).getOrderId().equals(id)).last();
    }

    public Mono<Boolean> deleteAll() {
        return this.reactiveRedisOperations.opsForList().delete(KEY);
    }
}
*/