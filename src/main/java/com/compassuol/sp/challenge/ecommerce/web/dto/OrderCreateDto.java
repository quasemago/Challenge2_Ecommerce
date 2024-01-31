package com.compassuol.sp.challenge.ecommerce.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreateDto {
    @Valid
    @NotEmpty
    private List<@NotNull ProductOrderDto> products;
    @Valid
    @NotNull
    private AddressCreateDto address;
    @NotBlank
    @Pattern(regexp = "CREDIT_CARD|BANK_TRANSFER|CRYPTOCURRENCY|GIFT_CARD|PIX|OTHER")
    private String paymentMethod;
}
