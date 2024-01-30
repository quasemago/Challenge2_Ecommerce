package com.compassuol.sp.challenge.ecommerce.domain.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressCreateDto {
    @NotNull
    private Integer number;
    @NotBlank
    private String complement;
    @NotBlank
    private String postalCode;
}
