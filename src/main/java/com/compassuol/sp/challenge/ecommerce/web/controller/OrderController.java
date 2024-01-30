package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Void> createOrder() {
        return null;
    }

    public ResponseEntity<Void> updateOrder() {
        return null;
    }

    public ResponseEntity<Void> cancelOrder() {
        return null;
    }
}
