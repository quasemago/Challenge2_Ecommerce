package com.compassuol.sp.challenge.ecommerce.domain.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.service.ProductService;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductCreateDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductResponseDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.mapper.ProductMapper;
import com.compassuol.sp.challenge.ecommerce.domain.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @Operation(summary = "Deletar produto pelo ID", description = "Recurso para deletar um produto pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}

