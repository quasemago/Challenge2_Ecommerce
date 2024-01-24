package com.compassuol.sp.challenge.ecommerce.web;

import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.service.ProductService;
import com.compassuol.sp.challenge.ecommerce.domain.web.controller.ProductController;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductCreateDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductResponseDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;

    private static ProductCreateDto createProductDto(Product p) {
        ProductCreateDto dto = new ProductCreateDto();
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setValue(p.getValue());
        return dto;
    }

    @Test
    public void createProduct_WithValidData_ReturnsProduct() throws Exception {
        final Product validProduct = Product.builder()
                .name("Product Valid")
                .description("Description Valid")
                .value(BigDecimal.valueOf(10.0))
                .build();

        when(productService.create(any())).thenReturn(validProduct);
        final ProductResponseDto dto = ProductMapper.toDto(validProduct);

        mockMvc.perform(
                        post("/products")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createProductDto(validProduct)))
                )
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.name").value(dto.getName()),
                        jsonPath("$.description").value(dto.getDescription()),
                        jsonPath("$.value").value(dto.getValue())
                );
    }

    @Test
    public void createProduct_WithInvalidData_ReturnsBadRequest() throws Exception {
        final Product invalidProduct = Product.builder()
                .name("")
                .description("short")
                .value(BigDecimal.valueOf(-10.0))
                .build();

        mockMvc.perform(
                        post("/products")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createProductDto(invalidProduct)))
                )
                .andExpect(status().isBadRequest());
    }
}
