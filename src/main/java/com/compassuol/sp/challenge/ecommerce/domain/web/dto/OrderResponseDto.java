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
public class OrderResponseDto {

    private Long id;
    private List<ProductCreateDto> products;
    private AddressDto address;
    private String paymentMethod;
    private BigDecimal subtotalValue;
    private BigDecimal discount;
    private BigDecimal totalValue;
    private LocalDateTime createdDate;
    private String status;

    public static OrderResponseDto fromOrder(OrderCreateDto orderDTO) {
        return new OrderResponseDto(
                orderDTO.getId(),
                orderDTO.getProducts(),
                orderDTO.getAddress(),
                orderDTO.getPaymentMethod(),
                orderDTO.getSubtotalValue(),
                orderDTO.getDiscount(),
                orderDTO.getTotalValue(),
                orderDTO.getCreatedDate(),
                orderDTO.getStatus()
        );
    }
}
