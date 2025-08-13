package com.chakir.mockmvc;

import com.chakir.mockmvc.entities.Product;
import com.chakir.mockmvc.repositories.ProductRepository;
import com.chakir.mockmvc.services.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/application-test.yml")
class MockMvcApplicationTests {
    @Autowired
    private ProductRepository productRepository;
    @Mock
    private ProductService productService;

    @Test
    @DisplayName("Should have at least 3 products from liquibase")
    void shouldHaveAtLeastThreeProducts() {
        var products = productRepository.findAll();
        assertEquals(3, products.size(), "Should have at least 3 products");
    }

    @Test
    @DisplayName("Should return all products from the service")
    void shouldReturnAllProducts() {
        Product product1 = new Product(1L, "Laptop", 1200.00);
        Product product2 = new Product(2L, "Smartphone", 800.00);

        List<Product> products = List.of(product1, product2);
        when(productService.getProducts()).thenReturn(products);
        assertIterableEquals(products, productService.getProducts());
    }
}
