package com.example.Zalando.service;

import com.example.Zalando.model.Product;
import com.example.Zalando.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    public List<Product> filterProductsByCategoryName(String category) {
        return switch (category) {
            case "clothing" -> productRepository.findByCategoryName("clothing");
            case "pants" -> productRepository.findByCategoryName("pants");
            case "accessories" -> productRepository.findByCategoryName("accessories");
            case "shoes" -> productRepository.findByCategoryName("shoes");
            default -> productRepository.findAll();
        };
    }

    //TODO filter and sort must work together
    public List<Product> sortProducts(String sortOption) {
        List<Product> sortedProducts;
        String[] parts = sortOption.split("_");
        String sortBy = parts[0];
        String sortOrder = parts[1];
        sortedProducts = productRepository.findAll();
        switch (sortBy) {
            case "name" -> {
                if ("asc".equals(sortOrder)) {
                    return productRepository.findAllByOrderByNameAsc();
                } else {
                    return productRepository.findAllByOrderByNameDesc();
                }
            }
            case "price" -> {
                if ("asc".equals(sortOrder)) {
                    return productRepository.findAllByOrderByPriceAsc();
                } else {
                    return productRepository.findAllByOrderByPriceDesc();
                }
            }
        }
        return sortedProducts;
    }

    public Product getProductById(int productId) {
        return productRepository.findById(productId).orElse(null);
    }

    //todo pagination
    public Page<Product> getAllProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}