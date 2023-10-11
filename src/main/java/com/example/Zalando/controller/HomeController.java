package com.example.Zalando.controller;

import com.example.Zalando.service.BasketProductService;
import com.example.Zalando.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/zalando")
public class HomeController {
    private final ProductService productService;
    private final BasketProductService basketProductService;

    @GetMapping("/home")
    public String showAllProducts(Model model) {
        model.addAttribute("entities", productService.getAllProducts());
        return "home";
    }

    @GetMapping("/filter")
    public String filterProducts(@RequestParam(name = "filter", defaultValue = "all") String filterBy, Model model) {
        model.addAttribute("entities", productService.filterProductsByCategoryName(filterBy));
        return "home";
    }

    @GetMapping("/sort")
    public String sortProducts(@RequestParam(name = "sort", defaultValue = "name_asc") String sortOption, Model model) {
        model.addAttribute("entities", productService.sortProducts(sortOption));
        return "home";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(name = "name") String name, Model model) {
        model.addAttribute("entities", productService.searchProductsByName(name));
        return "home";
    }
}