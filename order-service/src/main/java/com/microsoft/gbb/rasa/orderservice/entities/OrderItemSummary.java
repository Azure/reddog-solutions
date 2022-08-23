package com.microsoft.gbb.rasa.orderservice.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@ToString
@Entity
@Table(name = "order_item_summary")
public class OrderItemSummary {
    @Column(name = "product_name")
    private String productName;

    @ManyToMany
    @JoinTable(name = "order_item_summary_products",
            joinColumns = @JoinColumn(name = "order_item_summary_id"),
            inverseJoinColumns = @JoinColumn(name = "products_product_id"))
    private Collection<Product> products = new ArrayList<>();

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_cost", nullable = false)
    private double unitCost;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "order_summary_order_id")
    private OrderSummary orderSummary;

    @Id
    @Column(name = "order_summary_id", nullable = false)
    private Long orderSummaryId;

}