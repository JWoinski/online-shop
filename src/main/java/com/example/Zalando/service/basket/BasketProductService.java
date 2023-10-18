package com.example.Zalando.service.basket;

import com.example.Zalando.model.Product;
import com.example.Zalando.model.basket.Basket;
import com.example.Zalando.model.basket.BasketProduct;
import com.example.Zalando.repository.BasketProductRepository;
import com.example.Zalando.service.ProductService;
import com.example.Zalando.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketProductService {
    private final BasketProductRepository basketProductRepository;
    private final ProductService productService;
    private final UserService userService;
    private final BasketService basketService;

    public void addToBasket(int productId, int quantity) {
        Basket basket = getBasketForCurrentUser();

        Optional<BasketProduct> existingProductOptional = basketProductRepository.findByProductProductId(productId);

        Product product = productService.getProductById(productId);
        if (existingProductOptional.isPresent()) {
            BasketProduct existingProduct = existingProductOptional.get();
            int availableQuantity = product.getQuantity();
            if (existingProduct.getQuantity() + quantity <= availableQuantity) {
                existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
                updateQuantityInDb(existingProduct);
            } else {
                existingProduct.setQuantity(availableQuantity);
                updateQuantityInDb(existingProduct);
            }
        } else {
            int availableQuantity = product.getQuantity();
            if (quantity <= availableQuantity) {
                updateQuantityInDb(new BasketProduct(basket, product, quantity));
            }
        }
    }

    private Basket getBasketForCurrentUser() {
        basketService.checkActiveBasket(userService.getCurrentLoggedUser());
        return basketService.getActiveBasket(userService.getCurrentLoggedUser()).orElseThrow(() -> new EntityNotFoundException(String.valueOf(userService.getCurrentLoggedUser())));
    }

    public List<BasketProduct> getBasketProducts() {
        return basketProductRepository.findByBasket(getBasketForCurrentUser());
    }

    public void updateQuantityInDb(BasketProduct basketProduct) {
        basketProductRepository.save(basketProduct);
    }
}