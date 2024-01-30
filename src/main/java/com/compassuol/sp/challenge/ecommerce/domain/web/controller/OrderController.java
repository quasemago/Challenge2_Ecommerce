package com.compassuol.sp.challenge.ecommerce.domain.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.OrderCreateDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.OrderResponseDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.mapper.OrderMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders API", description = "Contém as operações relativas ao domínio pedidos. " +
        "Permite que os usuários criem, leiam, atualizem e cancelem pedidos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public ResponseEntity<Void> getAllOrders() {
        return null;
    }

    public ResponseEntity<Void> getOrderById() {
        return null;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderCreateDto createDto) {
        final Order order = orderService.create(createDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderMapper.toDto(order));
    }

    public ResponseEntity<Void> updateOrder() {
        return null;
    }

    public ResponseEntity<Void> cancelOrder() {
        return null;
    }
}
