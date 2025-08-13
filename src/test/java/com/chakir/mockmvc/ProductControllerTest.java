package com.chakir.mockmvc;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.yml")
@AutoConfigureMockMvc
@Slf4j
public class ProductControllerTest {
    private static MockHttpServletRequest request;
    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("Should create product via HTTP POST")
    void createProductHttpRequest() throws Exception {
        String productJson = """
                    {
                        "name": "Sample Product",
                        "price": 19.99
                    }
                """;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        log.info("Create Product Response: " + responseContent);
    }
}
