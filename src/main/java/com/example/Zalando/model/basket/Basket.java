package com.example.Zalando.model.basket;

import com.example.Zalando.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int basketId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String flag;

    public Basket(String flag, User user) {
        this.flag = flag;
        this.user = user;
    }
}