package com.compassuol.sp.challenge.ecommerce.domain.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressResponseDto {
    private Integer number;
    private String postalCode;
    private String street;
    private String complement;
    private String city;
    private String state;
}
