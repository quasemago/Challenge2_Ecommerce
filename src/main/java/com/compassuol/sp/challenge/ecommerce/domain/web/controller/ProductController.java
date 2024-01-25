package com.compassuol.sp.challenge.ecommerce.domain.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.service.ProductService;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductCreateDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductResponseDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.mapper.ProductMapper;
import com.compassuol.sp.challenge.ecommerce.domain.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Products API", description = "Contém as operações relativas ao domínio produtos. " +
        "Permite que os usuários criem, leiam, atualizem e excluam produtos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Lista todos os produtos.", description = "Recurso para recuperar uma lista de todos os produtos cadastrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de produtos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(ProductMapper.toDtoList(products));
    }

    @Operation(summary = "Recuperar informações de um produto existente.", description = "Recurso para recuperar um produto existente através do Id.",
            parameters = {
                    @Parameter(name = "id", description = "Identificador (Id) do produto no banco de dados.",
                            in = ParameterIn.PATH, required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @Operation(summary = "Criar um novo produto.", description = "Recurso para criar um novo produto.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "409", description = "Nome do produto já existente.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductCreateDto dto) {
        final Product product = productService.create(ProductMapper.toProduct(dto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductMapper.toDto(product));
    }

    @Operation(summary = "Atualizar um produto existente.", description = "Recurso para atualizar as informações de um produto existente através do Id.",
            parameters = {
                    @Parameter(name = "id", description = "Identificador (Id) do produto no banco de dados.",
                            in = ParameterIn.PATH, required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductCreateDto dto) {
        Product updatedProduct = productService.update(ProductMapper.toProduct(dto), id);
        return ResponseEntity.ok(ProductMapper.toDto(updatedProduct));
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

