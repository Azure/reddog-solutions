package com.microsoft.gbb.reddog.orderservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_item_summary")
public class OrderItemSummary {
    @Id
    @Column(name = "order_summary_id", nullable = false)
    private Long orderSummaryId;

    @Column(name = "product_name")
    private String productName;

    @ManyToMany
    @JoinTable(name = "order_item_summary_products",
            joinColumns = @JoinColumn(name = "order_item_summary_id"),
            inverseJoinColumns = @JoinColumn(name = "products_product_id"))
    @ToString.Exclude
    private Collection<Product> products = new ArrayList<>();

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_cost", nullable = false)
    private double unitCost;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "order_summary_order_id")
    private OrderSummary orderSummary;

    @ManyToOne
    @JoinColumn(name = "customer_order_id")
    private CustomerOrder customerOrder;

}