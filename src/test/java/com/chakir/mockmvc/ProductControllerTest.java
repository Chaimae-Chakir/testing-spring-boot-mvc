package com.chakir.mockmvc;

import com.chakir.mockmvc.entities.Product;
import com.chakir.mockmvc.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.yml")
@AutoConfigureMockMvc
@Slf4j
public class ProductControllerTest {
    private static MockHttpServletRequest request;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
        request.addParameter("productName", "testProduct");
        request.addParameter("productPrice", "100");
    }

    @Test
    @DisplayName("GET /products should return a list of products")
    void shouldReturnProducts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "products");
    }

    @Test
    @DisplayName("GET /products/create should show create form")
    void shouldShowCreateForm() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/products/create"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "create-product");
    }

    @Test
    @DisplayName("Should create product via HTTP POST")
    void createProductHttpRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Sample Product")
                        .param("price", "19.99"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "product-detail");

        Optional<Product> verifyProduct = productRepository.findByName("Sample Product");
        assertNotNull(verifyProduct.orElse(null), "Product should be found");
    }
}
