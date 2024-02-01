package com.compassuol.sp.challenge.ecommerce.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class AddressCreateDto {
    @NotNull
    private Integer number;
    @NotBlank
    private String complement;
    @NotBlank
    @Length(min = 8, max = 8, message = "O código postal deve ter 8 caracteres.")
    @Pattern(regexp = "^[0-9]{8}$", message = "O código postal deve conter apenas números.")
    private String postalCode;
}
