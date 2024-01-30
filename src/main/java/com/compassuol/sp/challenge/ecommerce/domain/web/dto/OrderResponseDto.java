package com.compassuol.sp.challenge.ecommerce.domain.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private List<ProductOrderDto> products;
    private AddressResponseDto address;
    private String paymentMethod;
    private BigDecimal subtotalValue;
    private BigDecimal discount;
    private BigDecimal totalValue;
    private LocalDateTime createdDate;
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cancelReason;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime cancelDate;
}
