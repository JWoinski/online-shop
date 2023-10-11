package com.example.Zalando.service;

import com.example.Zalando.model.Product;
import com.example.Zalando.service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {

        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product("Clothing", "Shirt", "Description", 20.0, 10, "Shirt1"));
        mockProducts.add(new Product("Pants", "Jeans", "Description", 30.0, 15, "Jeans1"));

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.getAllProducts();

        assertEquals(mockProducts, result);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void searchProductsByName_shouldReturnMatchingProducts() {

        String searchName = "Shirt";
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product("Clothing", "Shirt", "Description", 20.0, 10, "Shirt1"));

        when(productRepository.findByNameContaining(searchName)).thenReturn(mockProducts);

        List<Product> result = productService.searchProductsByName(searchName);

        assertEquals(mockProducts, result);
        verify(productRepository, times(1)).findByNameContaining(searchName);
    }

    @Test
    void filterProductsByCategoryName_shouldReturnFilteredProducts() {
        String category = "clothing";
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product("Clothing", "Shirt", "Description", 20.0, 10, "Shirt1"));
        mockProducts.add(new Product("Clothing", "Jeans", "Description", 30.0, 15, "Jeans1"));

        when(productRepository.findByCategoryName(category)).thenReturn(mockProducts);

        List<Product> result = productService.filterProductsByCategoryName(category);

        assertEquals(mockProducts, result);
        verify(productRepository, times(1)).findByCategoryName(category);
    }

    @Test
    void sortProductsByNameAsc_shouldReturnSortedProducts() {
        // Arrange
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product("Clothing", "Shirt", "Description", 20.0, 10, "Shirt1"));
        mockProducts.add(new Product("Pants", "Jeans", "Description", 30.0, 15, "Jeans1"));

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.sortProducts("name_asc");

        mockProducts.sort(Comparator.comparing(Product::getName));
        assertEquals(mockProducts, result);
    }

    @Test
    void getProductById_shouldReturnProduct() {
        int productId = 0;
        Product mockProduct = new Product("Clothing", "Shirt", "Description", 20.0, 10, "Shirt1");

        when(productRepository.findByProductId(productId)).thenReturn(mockProduct);

        Product result = productService.getProductById(productId);

        assertEquals(mockProduct, result);
        verify(productRepository, times(1)).findByProductId(productId);
    }


}