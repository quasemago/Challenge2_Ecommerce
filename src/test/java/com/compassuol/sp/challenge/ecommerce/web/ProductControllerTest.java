package com.compassuol.sp.challenge.ecommerce.web;

import com.compassuol.sp.challenge.ecommerce.domain.product.exception.UniqueProductViolationException;
import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.service.ProductService;
import com.compassuol.sp.challenge.ecommerce.domain.web.controller.ProductController;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductCreateDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
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

import static com.compassuol.sp.challenge.ecommerce.domain.product.common.ProductsConstants.INVALID_PRODUCT;
import static com.compassuol.sp.challenge.ecommerce.domain.product.common.ProductsConstants.VALID_PRODUCT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        when(productService.create(any(Product.class))).thenReturn(VALID_PRODUCT);
        final ProductResponseDto responseBody = toResponseDto(VALID_PRODUCT);

        mockMvc.perform(
                        post("/products")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createProductDto(VALID_PRODUCT)))
                )
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.name").value(responseBody.getName()),
                        jsonPath("$.description").value(responseBody.getDescription()),
                        jsonPath("$.value").value(responseBody.getValue())
                );

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    public void createProduct_WithInvalidData_ReturnsBadRequest() throws Exception {
        mockMvc.perform(
                        post("/products")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createProductDto(INVALID_PRODUCT)))
                )
                .andExpect(status().isBadRequest());

        verify(productService, never()).create(any(Product.class));
    }

    @Test
    public void createProduct_WithDuplicateName_ReturnsConflict() throws Exception {
        when(productService.create(any(Product.class))).thenThrow(UniqueProductViolationException.class);

        mockMvc.perform(
                        post("/products")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createProductDto(VALID_PRODUCT)))
                )
                .andExpect(status().isConflict());

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    public void createProduct_WithInvalidFields_ReturnsInternalServerError() throws Exception {
        mockMvc.perform(
                        post("/products")
                                .contentType("application/json")
                                .content("{\"name\": \"Product 1\", \"description\": \"Description 1\", \"value\": \"teste\"}")
                )
                .andExpect(status().isInternalServerError());
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

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void getAllProducts_ReturnsEmptyList() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(
                        get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void getProductById_WithExistingId_ReturnsProduct() throws Exception {
        final Product product = Product.builder()
                .id(1L).name("Product 1").description("Description 1").value(BigDecimal.valueOf(10.0))
                .build();

        when(productService.getProductById(1L)).thenReturn(product);

        final ProductResponseDto dto = toResponseDto(product);

        mockMvc.perform(
                        get("/products/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.name").value(dto.getName()),
                        jsonPath("$.description").value(dto.getDescription()),
                        jsonPath("$.value").value(dto.getValue())
                );

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void getProductById_WithNonExistingId_ReturnsNotFound() throws Exception {
        when(productService.getProductById(1L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                        get("/products/{id}", 1L)
                )
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void deleteProduct_WithExistingId_ReturnsNoContent() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(
                        delete("/products/{id}", 1L)
                )
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    public void deleteProduct_WithNonExistingId_ReturnsNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(productService).deleteProduct(1L);

        mockMvc.perform(
                        delete("/products/{id}", 1L)
                )
                .andExpect(status().isNotFound());

        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    public void updateProduct_WithValidData_ReturnsProduct() throws Exception {
        when(productService.update(any(Product.class), eq(1L))).thenReturn(VALID_PRODUCT);
        final ProductResponseDto responseBody = toResponseDto(VALID_PRODUCT);

        mockMvc.perform(
                        put("/products/{id}", 1L)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createProductDto(VALID_PRODUCT)))
                )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.name").value(responseBody.getName()),
                        jsonPath("$.description").value(responseBody.getDescription()),
                        jsonPath("$.value").value(responseBody.getValue())
                );

        verify(productService, times(1)).update(any(Product.class), eq(1L));
    }

    @Test
    public void updateProduct_WithInvalidData_ReturnsBadRequest() throws Exception {
        mockMvc.perform(
                        put("/products/{id}", 1L)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createProductDto(INVALID_PRODUCT)))
                )
                .andExpect(status().isBadRequest());

        verify(productService, never()).update(any(Product.class), eq(1L));
    }

    @Test
    public void updateProduct_WithNonExistingId_ReturnsNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(productService).update(any(Product.class), eq(1L));

        mockMvc.perform(
                        put("/products/{id}", 1L)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createProductDto(VALID_PRODUCT)))
                )
                .andExpect(status().isNotFound());

        verify(productService, times(1)).update(any(Product.class), eq(1L));
    }
}
