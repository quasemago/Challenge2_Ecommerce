package com.compassuol.sp.challenge.ecommerce.domain.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.product.service.OrdersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Products API", description = "Contém as operações relativas ao domínio produtos. " +
        "Permite que os usuários criem, leiam, atualizem e excluam produtos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

}
