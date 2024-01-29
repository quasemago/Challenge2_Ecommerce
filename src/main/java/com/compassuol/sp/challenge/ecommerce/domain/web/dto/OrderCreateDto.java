package com.compassuol.sp.challenge.ecommerce.domain.web.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class OrderCreateDto {
    
    private Long id;
    private List<ProductResponseDto> products;
    private AddressDto address;
    private String paymentMethod;
    private BigDecimal subtotalValue;
    private BigDecimal discount;
    private BigDecimal totalValue;
    private LocalDateTime createdDate;
    private String status;
    
}
