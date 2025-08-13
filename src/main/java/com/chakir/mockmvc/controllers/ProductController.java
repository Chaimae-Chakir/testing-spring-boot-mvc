package com.chakir.mockmvc.controllers;

import com.chakir.mockmvc.entities.Product;
import com.chakir.mockmvc.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public String getByID(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping
    public String getProducts(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @PostMapping
    public String createProduct(@ModelAttribute("product") Product product) {
        return "product-detail";
    }
}
