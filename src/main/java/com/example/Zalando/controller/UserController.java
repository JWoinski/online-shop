package com.example.Zalando.controller;

import com.example.Zalando.model.User;
import com.example.Zalando.service.ProductService;
import com.example.Zalando.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/zalando")
public class UserController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/registerForm")
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute("User") User user) {
        userService.saveUserToDB(user);
        model.addAttribute("entities", productService.getAllProducts());

        return "home";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("User") User user) {
        userService.loginUser(user);
        model.addAttribute("entities", productService.getAllProducts());

        return "home";
    }
}