package com.compassuol.sp.challenge.ecommerce.domain.web.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;



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

