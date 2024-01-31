package com.compassuol.sp.challenge.ecommerce.domain.order.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}

