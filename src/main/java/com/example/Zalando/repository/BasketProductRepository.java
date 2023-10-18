package com.example.Zalando.repository;

import com.example.Zalando.model.basket.Basket;
import com.example.Zalando.model.basket.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketProductRepository extends JpaRepository<BasketProduct, Integer> {

    Optional<BasketProduct> findByProductProductId(int productId);

    List<BasketProduct> findByBasket(Basket basket);
}
