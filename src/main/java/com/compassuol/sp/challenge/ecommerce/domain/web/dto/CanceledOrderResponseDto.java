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
@EqualsAndHashCode(callSuper = true)
public class CanceledOrderResponseDto extends OrderResponseDto {

    private String cancelReason;
    private LocalDateTime cancelDate;

    public CanceledOrderResponseDto(Long id, List<ProductResponseDto> products, AddressDto address,
                                    String paymentMethod, BigDecimal subtotalValue, BigDecimal discount,
                                    BigDecimal totalValue, LocalDateTime createdDate, String status,
                                    String cancelReason, LocalDateTime cancelDate) {
        super(id, products, address, paymentMethod, subtotalValue, discount, totalValue, createdDate, status);
        this.cancelReason = cancelReason;
        this.cancelDate = cancelDate;
    }

    public static CanceledOrderResponseDto fromOrder(OrderCreateDto orderDTO, String cancelReason, LocalDateTime cancelDate) {
        return new CanceledOrderResponseDto(
                orderDTO.getId(),
                orderDTO.getProducts(),
                orderDTO.getAddress(),
                orderDTO.getPaymentMethod(),
                orderDTO.getSubtotalValue(),
                orderDTO.getDiscount(),
                orderDTO.getTotalValue(),
                orderDTO.getCreatedDate(),
                orderDTO.getStatus(),
                cancelReason,
                cancelDate
        );
    }
}
