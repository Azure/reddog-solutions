package com.microsoft.gbb.reddog.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_summary")
public class OrderSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    
    @Column(name = "order_completed_date")
    private LocalDate orderCompletedDate;

    @Column(name = "loyalty_id")
    private String loyaltyId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "store_id")
    private String storeId;
    
    
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "order_total", nullable = false)
    private double orderTotal;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("storeLatitude")
    private String storeLatitude;

    @JsonProperty("storeLongitude")
    private String storeLongitude;

    @OneToMany(mappedBy = "orderSummary", orphanRemoval = true)
    @ToString.Exclude
    private List<OrderItemSummary> orderItemSummaries = new ArrayList<>();
}