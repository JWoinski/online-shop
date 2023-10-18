package com.example.Zalando.repository;

import com.example.Zalando.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNameContaining(String text);

    List<Product> findByCategoryName(String clothing);

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findAllByOrderByPriceDesc();

    List<Product> findAllByOrderByNameAsc();

    List<Product> findAllByOrderByNameDesc();
}
