package com.example.Zalando.service.repository;

import com.example.Zalando.model.Basket;
import com.example.Zalando.model.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketProductRepository extends JpaRepository<BasketProduct, Integer> {

    Optional<BasketProduct> findByProductProductId(int productId);

    List<BasketProduct> findByBasket(Basket basket);
}
