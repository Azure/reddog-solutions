package com.microsoft.gbb.reddog.loyaltyservice.repository;

import com.microsoft.gbb.reddog.loyaltyservice.model.LoyaltySummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LoyaltySummaryRepository {
    public static final String KEY = "loyaltysummary";
    private final ReactiveRedisOperations<String, LoyaltySummary> reactiveRedisOperations;

    public Flux<LoyaltySummary> findAll(){
        return this.reactiveRedisOperations.opsForList().range(KEY, 0, -1);
    }

    public Mono<LoyaltySummary> findById(String id) {
        return this.findAll().filter(p -> p.getLoyaltyId().equals(id)).last();
    }

    public Mono<Long> save(LoyaltySummary loyaltySummary){
        return this.reactiveRedisOperations.opsForList().rightPush(KEY, loyaltySummary);
    }

    public Mono<Boolean> deleteAll() {
        return this.reactiveRedisOperations.opsForList().delete(KEY);
    }
}
