package com.example.Zalando.service.basket;

import com.example.Zalando.model.Product;
import com.example.Zalando.model.basket.BasketAction;
import com.example.Zalando.model.basket.BasketProduct;
import com.example.Zalando.repository.BasketProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class BasketQuantityManager {
    private BasketProductRepository basketProductRepository;
    private Map<BasketAction, Consumer<BasketProduct>> actionMap;

    @PostConstruct
    private void initActionMap() {
        actionMap = Map.of(BasketAction.INCREASE, this::increaseQuantity,
                BasketAction.DECREASE, this::decreaseQuantity,
                BasketAction.REMOVE, this::removeProduct);
    }

    public void updateBasket(int productId, String action) {
        initActionMap();
        BasketProduct basketProduct = basketProductRepository.findByProductProductId(productId).orElse(null);
        BasketAction basketAction = BasketAction.valueOf(action.toUpperCase());
        actionMap.getOrDefault(basketAction, this::unknownAction).accept(basketProduct);
    }

    public void addActionHandler(BasketAction action, Consumer<BasketProduct> handler) {
        actionMap.put(action, handler);
    }

    public void increaseQuantity(BasketProduct basketProduct) {
        Product product = basketProduct.getProduct();
        int availableQuantity = product.getQuantity();
        if (basketProduct.getQuantity() + 1 <= availableQuantity) {
            basketProduct.setQuantity(basketProduct.getQuantity() + 1);
            basketProductRepository.save(basketProduct);
        }
    }


    public void decreaseQuantity(BasketProduct basketProduct) {
        int newQuantity = basketProduct.getQuantity() - 1;
        if (newQuantity > 0) {
            basketProduct.setQuantity(newQuantity);
            basketProductRepository.save(basketProduct);
        }
    }

    public void removeProduct(BasketProduct basketProduct) {
        basketProductRepository.delete(basketProduct);
    }


    private void unknownAction(BasketProduct basketProduct) {
        throw new IllegalArgumentException("Nieznana akcja");
    }
}