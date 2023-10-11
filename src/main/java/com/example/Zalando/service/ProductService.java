package com.example.Zalando.service;

import com.example.Zalando.model.Product;
import com.example.Zalando.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public List<Product> sortProducts(String sortOption) {
        List<Product> sortedProducts;

        String[] parts = sortOption.split("_");
        String sortBy = parts[0];
        String sortOrder = parts[1];
        sortedProducts = productRepository.findAll();
        //TODO SORTED FROM SQL
        switch (sortBy) {
            case "name" -> {
//                sortedProducts = productRepository.findAll();
                if ("asc".equals(sortOrder)) {
                    return productRepository.findAllByOrderByNameAsc();
//                    sortedProducts.sort(Comparator.comparing(Product::getName));
                }
                else {
                    return productRepository.findAllByOrderByNameDesc();
//                    sortedProducts.sort(Comparator.comparing(Product::getName).reversed());
                }
            }
            case "price" -> {
//                sortedProducts = productRepository.findAll();
                if ("asc".equals(sortOrder)) {
                    return productRepository.findAllByOrderByPriceAsc();
//                    sortedProducts.sort(Comparator.comparing(Product::getPrice));
                } else {
                    return productRepository.findAllByOrderByPriceDesc();
//                    sortedProducts.sort(Comparator.comparing(Product::getPrice).reversed());
                }
            }

        }
        return sortedProducts;
    }
    public Product getProductById(int productId) {
        return productRepository.findById(productId).orElse(null);
    }
}