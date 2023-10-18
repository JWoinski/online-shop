package com.example.Zalando.controller;

import com.example.Zalando.service.ProductService;
import com.example.Zalando.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"filterOption", "sortOption"})
@RequiredArgsConstructor
@RequestMapping("/zalando")
public class HomeController {
    private final ProductService productService;
    private final UserService userService;
    //todo pagination
    //    @GetMapping("/home")
//    public String showAllProducts(@RequestParam(defaultValue = "0") int page, Model model) {
//        int pageSize = 10;
//        Page<Product> productPage = productService.getAllProductsPaginated(PageRequest.of(page, pageSize));
//
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", productPage.getTotalPages());
//        model.addAttribute("products", productPage.getContent());
//
//        return "home";
//    }
    @GetMapping("/home")
    public String showAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "home";
    }

    @GetMapping("/filter")
    public String filterProducts(@RequestParam(name = "filter", defaultValue = "all") String filterBy, Model model) {
        model.addAttribute("products", productService.filterProductsByCategoryName(filterBy));
        return "home";
    }

    @GetMapping("/sort")
    public String sortProducts(@RequestParam(name = "sort", defaultValue = "name_asc") String sortOption, Model model) {
        model.addAttribute("products", productService.sortProducts(sortOption));
        return "home";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(name = "name") String name, Model model) {
        model.addAttribute("products", productService.searchProductsByName(name));
        return "home";
    }
}