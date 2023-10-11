package com.example.Zalando.controller;

import com.example.Zalando.model.BasketProduct;
import com.example.Zalando.service.BasketProductService;
import com.example.Zalando.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/zalando")
public class BasketController {
    private final BasketProductService basketProductService;
    private final BasketService basketService;
    private final ProductDetailsController productDetailsController;

    @GetMapping("/basket")
    public String viewBasket(Model model) {

        List<BasketProduct> basketProducts = basketProductService.getBasketProducts();

        model.addAttribute("basketProducts", basketProducts);
        model.addAttribute("totalSum", basketService.calculateTotal(basketProducts));

        return "basket";
    }

    @PostMapping("/addToBasket")
    public String addToBasket(@RequestParam("productId") int productId,
                              @RequestParam("quantity") int quantity, Model model) {
        basketProductService.addToBasket(productId, quantity);
        return productDetailsController.showProductDetails(productId, model);
    }
    @PostMapping("/updateBasket")
    public String updateBasket(@RequestParam("productId") int productId,
                               @RequestParam String action) {
        basketProductService.updateBasket(productId, action);
        return "redirect:/zalando/basket";
    }
}