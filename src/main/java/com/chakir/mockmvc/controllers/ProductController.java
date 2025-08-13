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

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("newProduct", new Product());
        return "create-product";
    }

    @PostMapping
    public String createProduct(@ModelAttribute("newProduct") Product product, Model model) {
        Product savedProduct = productService.createProduct(product);
        model.addAttribute("product", savedProduct);
        return "product-detail";
    }
}
