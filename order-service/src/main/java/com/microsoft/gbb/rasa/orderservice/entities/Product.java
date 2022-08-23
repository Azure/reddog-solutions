package com.microsoft.gbb.rasa.orderservice.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product {
    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "unit_cost", nullable = false)
    private double unitCost;

    @Column(name = "image_url")
    private String imageUrl;
}