package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.domain.order.model.Address;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.OrderProduct;
import com.compassuol.sp.challenge.ecommerce.web.dto.AddressResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderCancelDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProductOrderDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {
    public static OrderResponseDto toDto(Order order){
        return new ModelMapper().map(order, OrderResponseDto.class);
    }

}
