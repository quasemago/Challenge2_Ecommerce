package com.compassuol.sp.challenge.ecommerce.domain.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductOrderDto {
    private Long productId;
    private Integer quantity;
}
