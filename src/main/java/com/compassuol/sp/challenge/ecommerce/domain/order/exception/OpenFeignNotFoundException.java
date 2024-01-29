package com.compassuol.sp.challenge.ecommerce.domain.order.exception;

public class OpenFeignNotFoundException extends RuntimeException{
    public OpenFeignNotFoundException(String message) {
        super(message);
    }
}
