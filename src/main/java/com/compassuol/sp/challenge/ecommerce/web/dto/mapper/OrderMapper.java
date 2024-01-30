package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {
    public static OrderResponseDto toDto(Order order){
        return new ModelMapper().map(order, OrderResponseDto.class);
    }

    public static List<OrderResponseDto> toDtoList(List<Order> orders){
        return orders.stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }
}
