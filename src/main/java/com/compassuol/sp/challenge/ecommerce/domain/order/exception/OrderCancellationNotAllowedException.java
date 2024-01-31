package com.compassuol.sp.challenge.ecommerce.domain.order.exception;

 public class OrderCancellationNotAllowedException extends RuntimeException {
    public OrderCancellationNotAllowedException(String message) {
        super(message);
    }
}

