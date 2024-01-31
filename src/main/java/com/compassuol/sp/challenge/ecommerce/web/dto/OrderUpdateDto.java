package com.compassuol.sp.challenge.ecommerce.web.dto;

import com.compassuol.sp.challenge.ecommerce.domain.order.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderUpdateDto {

    @NotNull

    private OrderStatus status;
}
