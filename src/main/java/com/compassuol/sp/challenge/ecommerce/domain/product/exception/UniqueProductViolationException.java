package com.compassuol.sp.challenge.ecommerce.domain.product.exception;

public class UniqueProductViolationException extends RuntimeException {
    public UniqueProductViolationException(String message) {
        super(message);
    }
}
