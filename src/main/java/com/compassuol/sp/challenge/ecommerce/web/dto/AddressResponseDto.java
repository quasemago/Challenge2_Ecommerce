package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressResponseDto {
    private String street;
    private Integer number;
    private String complement;
    private String city;
    private String state;
    private String postalCode;
}
