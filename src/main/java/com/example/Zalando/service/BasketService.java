package com.example.Zalando.service;

import com.example.Zalando.model.BasketProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {
    public double calculateTotal(List<BasketProduct> basketProducts) {
        return basketProducts.stream()
                .mapToDouble(basketProduct -> basketProduct.getProduct().getPrice() * basketProduct.getQuantity())
                .sum();
    }
}