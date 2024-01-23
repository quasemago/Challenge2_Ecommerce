package com.compassuol.sp.challenge.ecommerce.domain.web.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal value;
}
