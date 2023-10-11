package com.example.Zalando.service;

import com.example.Zalando.model.Basket;
import com.example.Zalando.model.BasketProduct;
import com.example.Zalando.model.Product;
import com.example.Zalando.service.repository.BasketProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BasketProductServiceTest {

    @Mock
    private BasketProductRepository basketProductRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private BasketProductService basketProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addToBasket_existingProduct_shouldIncreaseQuantity() {
        int productId = 1;
        int quantity = 2;

        Product mockProduct = new Product("Clothing", "Shirt", "Description", 20.0, 10, "Shirt1");
        BasketProduct existingProduct = new BasketProduct(new Basket(), mockProduct, 3);

        when(productService.getProductById(productId)).thenReturn(mockProduct);
        when(basketProductRepository.findByProductProductId(productId)).thenReturn(Optional.of(existingProduct));

        basketProductService.addToBasket(productId, quantity);

        verify(basketProductRepository, times(1)).save(existingProduct);
    }


    @Test
    void addToBasket_newProduct_shouldSaveNewProduct() {
        int productId = 1;
        int quantity = 2;

        Product mockProduct = new Product("Clothing", "Shirt", "Description", 20.0, 10, "Shirt1");


        when(productService.getProductById(productId)).thenReturn(mockProduct);
        when(basketProductRepository.findByProductProductId(productId)).thenReturn(Optional.empty());

        basketProductService.addToBasket(productId, quantity);

        verify(basketProductRepository, times(1)).save(any(BasketProduct.class));
    }

    @Test
    void getBasketProducts_shouldReturnAllBasketProducts() {

        List<BasketProduct> mockBasketProducts = new ArrayList<>();
        mockBasketProducts.add(new BasketProduct(new Basket(), new Product(), 2));
        mockBasketProducts.add(new BasketProduct(new Basket(), new Product(), 3));

        when(basketProductRepository.findAll()).thenReturn(mockBasketProducts);

        List<BasketProduct> result = basketProductService.getBasketProducts();

        assertEquals(mockBasketProducts, result);
        verify(basketProductRepository, times(1)).findAll();
    }

    @Test
    void updateBasket_increaseQuantity_shouldIncreaseQuantity() {
        int productId = 1;
        int quantity = 2;

        Product mockProduct = new Product("Clothing", "Shirt", "Description", 20.0, 10, "Shirt1");
        BasketProduct existingProduct = new BasketProduct(new Basket(), mockProduct, 3);

        when(productService.getProductById(productId)).thenReturn(mockProduct);
        when(basketProductRepository.findByProductProductId(productId)).thenReturn(Optional.of(existingProduct));

        basketProductService.addToBasket(productId, quantity);

        verify(basketProductRepository, times(1)).save(existingProduct);
    }

    @Test
    void updateBasket_decreaseQuantity_shouldDecreaseQuantity() {
        int productId = 1;
        String action = "decrease:";
        BasketProduct mockBasketProduct = new BasketProduct(new Basket(), new Product(), 2);

        when(basketProductRepository.findByProductProductId(productId)).thenReturn(Optional.of(mockBasketProduct));

        basketProductService.updateBasket(productId, action);

        verify(basketProductRepository, times(1)).save(mockBasketProduct);
    }

    @Test
    void updateBasket_decreaseQuantity_belowZero_shouldNotDecreaseQuantity() {
        int productId = 1;
        String action = "decrease:";
        BasketProduct mockBasketProduct = new BasketProduct(new Basket(), new Product(), 0);

        when(basketProductRepository.findByProductProductId(productId)).thenReturn(Optional.of(mockBasketProduct));

        basketProductService.updateBasket(productId, action);

        verify(basketProductRepository, never()).save(any());
    }

    @Test
    void updateBasket_removeProduct_shouldRemoveProduct() {
        int productId = 1;
        String action = "remove:";
        BasketProduct mockBasketProduct = new BasketProduct(new Basket(), new Product(), 2);

        when(basketProductRepository.findByProductProductId(productId)).thenReturn(Optional.of(mockBasketProduct));

        basketProductService.updateBasket(productId, action);

        verify(basketProductRepository, times(1)).delete(mockBasketProduct);
    }
}