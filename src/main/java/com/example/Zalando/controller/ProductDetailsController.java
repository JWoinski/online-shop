package com.example.Zalando.controller;

import com.example.Zalando.service.OpinionService;
import com.example.Zalando.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/zalando")
public class ProductDetailsController {
    private final ProductService productService;
    private final OpinionService opinionService;

    @GetMapping("/product/{productId}")
    public String showProductDetails(@PathVariable int productId, Model model) {

        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("opinions", opinionService.getOpinionByProductId(productId));
        return "product";
    }
}