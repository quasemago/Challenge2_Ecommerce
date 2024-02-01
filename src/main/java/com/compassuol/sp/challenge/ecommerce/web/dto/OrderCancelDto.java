package com.compassuol.sp.challenge.ecommerce.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderCancelDto {
    @NotBlank
    private String cancelReason;

    public OrderCancelDto(String cancelReason) {
        this.cancelReason = cancelReason;
    }

}
