package com.compassuol.sp.challenge.ecommerce.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderCancelDto {
    private String cancelReason;
}
