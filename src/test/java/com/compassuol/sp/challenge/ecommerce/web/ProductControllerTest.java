package com.compassuol.sp.challenge.ecommerce.web;

import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.service.ProductService;
import com.compassuol.sp.challenge.ecommerce.domain.web.controller.ProductController;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductCreateDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private static ProductResponseDto toResponseDto(Product p) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setValue(p.getValue());
        return dto;
    }

    private static List<ProductResponseDto> toResponseDtoList(List<Product> products) {
        return products.stream()
                .map(ProductControllerTest::toResponseDto)
                .collect(Collectors.toList());
    }

    @Test
    public void createProduct_WithValidData_ReturnsProduct() throws Exception {
        final Product validProduct = Product.builder()
                .name("Product Valid").description("Description Valid").value(BigDecimal.valueOf(10.0))
                .build();

        when(productService.create(any(Product.class))).thenReturn(validProduct);
        final ProductResponseDto responseBody = toResponseDto(validProduct);

        mockMvc.perform(
                        post("/products")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createProductDto(validProduct)))
                )
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.name").value(responseBody.getName()),
                        jsonPath("$.description").value(responseBody.getDescription()),
                        jsonPath("$.value").value(responseBody.getValue())
                );
    }

    @Test
    public void createProduct_WithInvalidData_ReturnsBadRequest() throws Exception {
        final Product invalidProduct = Product.builder()
                .name("").description("short").value(BigDecimal.valueOf(-10.0))
                .build();

        mockMvc.perform(
                        post("/products")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createProductDto(invalidProduct)))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllProducts_ReturnsProductList() throws Exception {
        final List<Product> products = List.of(
                Product.builder()
                        .id(1L).name("Product 1").description("Description 1").value(BigDecimal.valueOf(10.0))
                        .build(),
                Product.builder()
                        .id(2L).name("Product 2").description("Description 2").value(BigDecimal.valueOf(20.0))
                        .build()
        );

        when(productService.getAllProducts()).thenReturn(products);
        final List<ProductResponseDto> listDto = toResponseDtoList(products);

        mockMvc.perform(
                        get("/products")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(listDto.size()))
                .andExpectAll(
                        jsonPath("$[0].name").value(listDto.get(0).getName()),
                        jsonPath("$[0].description").value(listDto.get(0).getDescription()),
                        jsonPath("$[0].value").value(listDto.get(0).getValue()),
                        jsonPath("$[1].name").value(listDto.get(1).getName()),
                        jsonPath("$[1].description").value(listDto.get(1).getDescription()),
                        jsonPath("$[1].value").value(listDto.get(1).getValue())
                );
    }

    @Test
    public void getAllProducts_ReturnsEmptyList() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(
                        get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
