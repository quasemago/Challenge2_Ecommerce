package com.compassuol.sp.challenge.ecommerce.domain.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class Address {
    @JsonProperty("cep")
    private String postalCode;
    @JsonProperty("logradouro")
    private String street;
    @JsonProperty("complemento")
    private String complement;
    @JsonProperty("localidade")
    private String city;
    @JsonProperty("uf")
    private String state;
}
