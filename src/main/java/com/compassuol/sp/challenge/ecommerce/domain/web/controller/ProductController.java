package com.compassuol.sp.challenge.ecommerce.domain.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById() {
       return null;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct() {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct() {
        return null;
    }
}

