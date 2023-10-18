package com.example.Zalando.service;

import com.example.Zalando.model.basket.BasketProduct;
import com.example.Zalando.model.Product;
import com.example.Zalando.repository.BasketRepository;
import com.example.Zalando.service.basket.BasketService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BasketServiceTest {

    @Test
    void calculateTotal_shouldReturnCorrectTotal() {
        BasketRepository mockBasketRepository = mock(BasketRepository.class);
        BasketService basketService = new BasketService(mockBasketRepository);

        Product mockProduct1 = mock(Product.class);
        when(mockProduct1.getPrice()).thenReturn(20.0);

        Product mockProduct2 = mock(Product.class);
        when(mockProduct2.getPrice()).thenReturn(15.0);

        BasketProduct mockBasketProduct1 = mock(BasketProduct.class);
        when(mockBasketProduct1.getProduct()).thenReturn(mockProduct1);
        when(mockBasketProduct1.getQuantity()).thenReturn(2);

        BasketProduct mockBasketProduct2 = mock(BasketProduct.class);
        when(mockBasketProduct2.getProduct()).thenReturn(mockProduct2);
        when(mockBasketProduct2.getQuantity()).thenReturn(3);

        List<BasketProduct> basketProducts = Arrays.asList(mockBasketProduct1, mockBasketProduct2);

        double total = basketService.calculateTotal(basketProducts);

        assertEquals(20.0 * 2 + 15.0 * 3, total, 0.001);
    }

    @Test
    void calculateTotal_shouldReturnZeroForEmptyBasket() {
        BasketRepository mockBasketRepository = mock(BasketRepository.class);
        BasketService basketService = new BasketService(mockBasketRepository);
        List<BasketProduct> emptyBasket = List.of();

        double total = basketService.calculateTotal(emptyBasket);

        assertEquals(0.0, total, 0.001);
    }
}