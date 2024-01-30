package com.compassuol.sp.challenge.ecommerce.domain.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateDto {
    @NotBlank(message = "O nome do produto não pode estar em branco.")
    private String name;
    @NotBlank(message = "A descrição do produto não pode estar em branco.")
    @Size(min = 10, message = "A descrição do produto deve ter pelo menos 10 caracteres.")
    private String description;
    @NotNull
    @Positive(message = "O valor do produto deve ser um número positivo.")
    private BigDecimal value;
}
