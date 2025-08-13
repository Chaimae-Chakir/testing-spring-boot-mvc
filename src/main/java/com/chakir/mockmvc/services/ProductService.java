package com.chakir.mockmvc.services;

import com.chakir.mockmvc.entities.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);

    List<Product> getProducts();
    
    Product createProduct(Product product);
}
