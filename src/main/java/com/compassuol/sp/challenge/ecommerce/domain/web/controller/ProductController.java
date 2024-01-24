package com.compassuol.sp.challenge.ecommerce.domain.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.service.ProductService;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductCreateDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductResponseDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "Products API", description = "Contém as operações relativas aos recursos de interação com o domínio produtos.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ResponseEntity<List<Product>> getAllProducts() {
        return null;
    }

    public ResponseEntity<Product> getProductById() {
        return null;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductCreateDto dto) {
        final Product product = productService.create(ProductMapper.toProduct(dto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductMapper.toDto(product));
    }

    public ResponseEntity<Product> updateProduct() {
        return null;
    }

    public ResponseEntity<Void> deleteProduct() {
        return null;
    }
}

