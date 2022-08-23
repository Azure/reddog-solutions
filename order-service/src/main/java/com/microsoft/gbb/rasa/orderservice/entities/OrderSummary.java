package com.microsoft.gbb.rasa.orderservice.entities;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@ToString
@Entity
@Table(name = "order_summary")
public class OrderSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Temporal(TemporalType.DATE)
    @Column(name = "order_completed_date")
    private Date orderCompletedDate;

    @Column(name = "loyalty_id")
    private String loyaltyId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "store_id")
    private String storeId;

    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "order_total", nullable = false)
    private double orderTotal;

    @OneToMany(mappedBy = "orderSummary", orphanRemoval = true)
    private List<OrderItemSummary> orderItemSummaries = new ArrayList<>();

    public void setOrderItemSummaries(List<OrderItemSummary> orderItemSummaries) {
        this.orderItemSummaries = orderItemSummaries;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLoyaltyId(String loyaltyId) {
        this.loyaltyId = loyaltyId;
    }

    public void setOrderCompletedDate(Date orderCompletedDate) {
        this.orderCompletedDate = orderCompletedDate;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}