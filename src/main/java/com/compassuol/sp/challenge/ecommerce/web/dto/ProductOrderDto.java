package com.compassuol.sp.challenge.ecommerce.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductOrderDto {
    @NotNull
    private Long productId;
    @NotNull
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1.")
    private Integer quantity;
}
