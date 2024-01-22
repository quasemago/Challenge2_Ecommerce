package com.compassuol.sp.challenge.ecommerce.domain.product.service;

import com.compassuol.sp.challenge.ecommerce.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
}
