package com.example.Zalando.service.basket;

import com.example.Zalando.model.User;
import com.example.Zalando.model.basket.Basket;
import com.example.Zalando.model.basket.BasketProduct;
import com.example.Zalando.repository.BasketRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;

    public double calculateTotal(List<BasketProduct> basketProducts) {
        double total = basketProducts.stream()
                .mapToDouble(basketProduct -> basketProduct.getProduct().getPrice() * basketProduct.getQuantity())
                .sum();
        return Math.round(total * 100.0) / 100.0;
    }

    public void checkActiveBasket(User currentLoggedUser) {
        basketRepository.findFirstByUserUserIdAndFlagOrderByBasketIdAsc(currentLoggedUser.getUserId(), "active")
                .orElseGet(() -> basketRepository.save(new Basket("active", currentLoggedUser)));
    }

    public Optional<Basket> getActiveBasket(User currentLoggedUser) {
        return Optional.of(basketRepository.findFirstByUserUserIdAndFlagOrderByBasketIdAsc(currentLoggedUser.getUserId(), "active")).orElseThrow(() -> new EntityNotFoundException(String.valueOf(currentLoggedUser)));
    }
}