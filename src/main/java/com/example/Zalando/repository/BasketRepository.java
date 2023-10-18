package com.example.Zalando.repository;

import com.example.Zalando.model.basket.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    Optional<Basket> findFirstByUserUserIdAndFlagOrderByBasketIdAsc(int userId, String flag);
}
