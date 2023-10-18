package com.example.Zalando.controller;

import com.example.Zalando.model.User;
import com.example.Zalando.service.ProductService;
import com.example.Zalando.service.user.UserService;
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

    @GetMapping("/loginForm")
    public String showloginForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute("User") User user) {
        userService.saveUserToDB(user);
        model.addAttribute("products", productService.getAllProducts());
        return "home";
    }
}