package com.example.Zalando.service;

import com.example.Zalando.model.User;
import com.example.Zalando.model.basket.Basket;
import com.example.Zalando.model.basket.BasketProduct;
import com.example.Zalando.model.Product;
import com.example.Zalando.service.basket.BasketProductService;
import com.example.Zalando.service.basket.BasketQuantityManager;
import com.example.Zalando.repository.BasketProductRepository;
import com.example.Zalando.service.basket.BasketService;
import com.example.Zalando.service.user.UserService;
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

    @Mock
    private UserService userService;

    @Mock
    private BasketService basketService;

    @InjectMocks
    private BasketProductService basketProductService;

    @InjectMocks
    private BasketQuantityManager basketQuantityManager;

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


        User mockUser = mock(User.class);
        when(userService.getCurrentLoggedUser()).thenReturn(mockUser);

        when(basketService.getActiveBasket(mockUser)).thenReturn(Optional.of(new Basket()));

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

        // Ustaw oczekiwane zachowanie mocka userService
        User mockUser = mock(User.class);
        when(userService.getCurrentLoggedUser()).thenReturn(mockUser);

        // Ustaw oczekiwane zachowanie mocka basketService
        Basket mockBasket = new Basket();
        when(basketService.getActiveBasket(mockUser)).thenReturn(Optional.of(mockBasket));

        basketProductService.addToBasket(productId, quantity);

        verify(basketProductRepository, times(1)).save(any(BasketProduct.class));
    }

    @Test
    void getBasketProducts_shouldReturnAllBasketProducts() {
        List<BasketProduct> mockBasketProducts = new ArrayList<>();
        mockBasketProducts.add(new BasketProduct(new Basket(), new Product(), 2));
        mockBasketProducts.add(new BasketProduct(new Basket(), new Product(), 3));

        when(basketProductRepository.findAll()).thenReturn(mockBasketProducts);

        // Ustaw oczekiwane zachowanie mocka userService
        User mockUser = mock(User.class);
        when(userService.getCurrentLoggedUser()).thenReturn(mockUser);

        // Ustaw oczekiwane zachowanie mocka basketService
        when(basketService.getActiveBasket(mockUser)).thenReturn(Optional.of(new Basket()));

        List<BasketProduct> result = basketProductService.getBasketProducts();

        // Debug logging
        System.out.println("MockUser: " + mockUser);
        System.out.println("Active Basket: " + basketService.getActiveBasket(mockUser));

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

        // Ustaw oczekiwane zachowanie mocka userService
        User mockUser = mock(User.class);
        when(userService.getCurrentLoggedUser()).thenReturn(mockUser);

        // Ustaw oczekiwane zachowanie mocka basketService
        when(basketService.getActiveBasket(mockUser)).thenReturn(Optional.of(new Basket()));

        basketProductService.addToBasket(productId, quantity);

        verify(basketProductRepository, times(1)).save(existingProduct);
    }

    @Test
    void updateBasket_decreaseQuantity_shouldDecreaseQuantity() {
        int productId = 1;
        String action = "decrease";
        BasketProduct mockBasketProduct = new BasketProduct(new Basket(), new Product(), 2);

        when(basketProductRepository.findByProductProductId(productId)).thenReturn(Optional.of(mockBasketProduct));

        basketQuantityManager.updateBasket(productId, action);

        verify(basketProductRepository, times(1)).save(mockBasketProduct);
    }

    @Test
    void updateBasket_decreaseQuantity_belowZero_shouldNotDecreaseQuantity() {
        int productId = 1;
        String action = "decrease";
        BasketProduct mockBasketProduct = new BasketProduct(new Basket(), new Product(), 0);

        when(basketProductRepository.findByProductProductId(productId)).thenReturn(Optional.of(mockBasketProduct));

        basketQuantityManager.updateBasket(productId, action);

        verify(basketProductRepository, never()).save(any());
    }

    @Test
    void updateBasket_removeProduct_shouldRemoveProduct() {
        int productId = 1;
        String action = "remove";
        BasketProduct mockBasketProduct = new BasketProduct(new Basket(), new Product(), 2);

        when(basketProductRepository.findByProductProductId(productId)).thenReturn(Optional.of(mockBasketProduct));

        // Ustaw oczekiwane zachowanie mocka userService
        User mockUser = mock(User.class);
        when(userService.getCurrentLoggedUser()).thenReturn(mockUser);

        // Ustaw oczekiwane zachowanie mocka basketService
        when(basketService.getActiveBasket(mockUser)).thenReturn(Optional.of(new Basket()));

        basketQuantityManager.updateBasket(productId, action);

        verify(basketProductRepository, times(1)).delete(mockBasketProduct);
    }
}