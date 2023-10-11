package com.example.Zalando.service.repository;

import com.example.Zalando.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContaining(String text);

    List<Product> findByOrderByName();

    List<Product> findByOrderByNameDesc();

    List<Product> findByOrderByPrice();

    List<Product> findByOrderByPriceDesc();

    List<Product> findByCategoryName(String clothing);

    Product findByProductId(int productId);

    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();
    List<Product> findAllByOrderByNameAsc();
    List<Product> findAllByOrderByNameDesc();
}
