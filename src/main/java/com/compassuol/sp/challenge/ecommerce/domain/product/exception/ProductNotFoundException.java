package com.compassuol.sp.challenge.ecommerce.domain.product.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}

