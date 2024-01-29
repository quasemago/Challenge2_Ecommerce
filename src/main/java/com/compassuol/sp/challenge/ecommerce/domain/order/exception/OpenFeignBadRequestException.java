package com.compassuol.sp.challenge.ecommerce.domain.order.exception;

public class OpenFeignBadRequestException extends RuntimeException{
    public OpenFeignBadRequestException(String message) {
        super(message);
    }
}
