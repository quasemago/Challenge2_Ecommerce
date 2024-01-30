package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreateDto {
    private List<ProductOrderDto> products;
    private AddressCreateDto address;
    private String paymentMethod;
}
