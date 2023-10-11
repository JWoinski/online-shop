package com.example.Zalando.service;

import com.example.Zalando.model.Basket;
import com.example.Zalando.model.BasketProduct;
import com.example.Zalando.model.Product;
import com.example.Zalando.model.User;
import com.example.Zalando.service.repository.BasketProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketProductService {
    private final BasketProductRepository basketProductRepository;
    private final ProductService productService;

    public void addToBasket(int productId, int quantity) {
//        Basket basket = getBasketForCurrentUser();
        //======================================================================================
        //TEMPORARY
        Basket basket = new Basket(1, new User(), "active");
        //====================================================
        Optional<BasketProduct> existingProductOptional = basketProductRepository.findByProductProductId(productId);

        Product product = productService.getProductById(productId);
        if (existingProductOptional.isPresent()) {
            BasketProduct existingProduct = existingProductOptional.get();
            int availableQuantity = product.getQuantity();

            if (existingProduct.getQuantity() + quantity <= availableQuantity) {
                existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
                basketProductRepository.save(existingProduct);
            } else {
//                throw new IllegalArgumentException("Nie można dodać tylu produktów, brak wystarczającej ilości.");
            }
        } else {
            int availableQuantity = product.getQuantity();

            if (quantity <= availableQuantity) {
                basketProductRepository.save(new BasketProduct(basket, product, quantity));
            } else {
//                throw new IllegalArgumentException("Nie można dodać tylu produktów, brak wystarczającej ilości.");
            }
        }
    }

    public List<BasketProduct> getBasketProducts() {
        return basketProductRepository.findAll();
    }


    public void updateBasket(int productId, String action) {
        Optional<BasketProduct> basketProductOptional = basketProductRepository.findByProductProductId(productId);
        BasketProduct basketProduct = basketProductOptional.orElse(null);
        //TODO MAP OR SWITCH !!ENUM
        if (action.startsWith("increase:")) {
            increaseQuantity(basketProduct);
        } else if (action.startsWith("decrease:")) {
            decreaseQuantity(basketProduct);
        } else if (action.startsWith("remove:")) {
            removeProduct(basketProduct);
        }
    }

    public void increaseQuantity(BasketProduct basketProduct) {
        Product product = basketProduct.getProduct();
        int availableQuantity = product.getQuantity();
        if (basketProduct.getQuantity() + 1 <= availableQuantity) {
            basketProduct.setQuantity(basketProduct.getQuantity() + 1);
            basketProductRepository.save(basketProduct);
        } else {
//            throw new IllegalArgumentException("Nie można zwiększyć ilości, brak wystarczającej ilości w magazynie.");
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

}