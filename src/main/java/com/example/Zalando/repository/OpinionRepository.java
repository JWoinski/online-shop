package com.example.Zalando.repository;

import com.example.Zalando.model.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpinionRepository extends JpaRepository<Opinion, Integer> {

    List<Opinion> findByProductProductId(int productId);
}
