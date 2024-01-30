package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.OrderMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(OrderMapper.toDto(order));
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
