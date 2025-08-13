package com.chakir.mockmvc.services;

import com.chakir.mockmvc.entities.Product;
import com.chakir.mockmvc.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}