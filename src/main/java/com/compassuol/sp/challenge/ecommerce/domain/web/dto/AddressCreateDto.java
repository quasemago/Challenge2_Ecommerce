package com.compassuol.sp.challenge.ecommerce.domain.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressCreateDto {
    private Integer number;
    private String complement;
    private String postalCode;
}
