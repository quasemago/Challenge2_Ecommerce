package com.compassuol.sp.challenge.ecommerce.domain.order.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CREDIT_CARD,
    BANK_TRANSFER,
    CRYPTOCURRENCY,
    GIFT_CARD,
    PIX,
    OTHER
}
