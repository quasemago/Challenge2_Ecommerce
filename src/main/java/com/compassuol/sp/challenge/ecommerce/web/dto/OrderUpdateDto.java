package com.compassuol.sp.challenge.ecommerce.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderUpdateDto {
    @NotNull
    @Pattern(regexp = "^(?:SENT|CANCELED|CONFIRMED)$")
    private String status;
}
