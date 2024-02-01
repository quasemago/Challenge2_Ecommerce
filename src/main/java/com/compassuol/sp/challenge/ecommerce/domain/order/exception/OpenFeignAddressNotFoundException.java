package com.compassuol.sp.challenge.ecommerce.domain.order.exception;

public class OpenFeignAddressNotFoundException extends RuntimeException{
    public OpenFeignAddressNotFoundException(String message) {
        super(message);
    }
}
