package com.compassuol.sp.challenge.ecommerce.domain.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AddressDto {
    private String street;
    private int number;
    private String complement;
    private String city;
    private String state;
    private String postalCode;
}
