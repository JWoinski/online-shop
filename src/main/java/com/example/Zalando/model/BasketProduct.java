package com.example.Zalando.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasketProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int basketProductId;
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    public BasketProduct(Basket basket, Product product, int quantity) {
        this.basket = basket;
        this.product = product;
        this.quantity = quantity;
    }
}