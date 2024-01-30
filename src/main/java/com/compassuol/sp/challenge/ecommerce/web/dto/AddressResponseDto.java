package com.compassuol.sp.challenge.ecommerce.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
