package com.example.Zalando.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "opinions")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String categoryName;
    private String name;
    private String description;
    private String image;
    private double price;
    private int quantity;
    //EXCLUDE TOSTRING
    @OneToMany(mappedBy = "product")
    private Set<Opinion> opinions;


    public Product(String categoryName, String description, String image, double price, int quantity, String name) {
        this.categoryName = categoryName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }
}