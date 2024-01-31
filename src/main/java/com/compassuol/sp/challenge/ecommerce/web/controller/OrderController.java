package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OrderCancellationNotAllowedException;
import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OrderNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProductResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Recuperar informações de um pedido existente.", description = "Recurso para recuperar um pedido existente através do Id.",
            parameters = {
                    @Parameter(name = "id", description = "Identificador (Id) do pedido no banco de dados.",
                            in = ParameterIn.PATH, required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
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

    @Operation(summary = "Deletar um produto existente.", description = "Recurso para deletar um produto existente do banco de dados através do Id.",
            parameters = {
                    @Parameter(name = "id", description = "Identificador (Id) do produto no banco de dados.",
                            in = ParameterIn.PATH, required = true),
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        try {
            OrderResponseDto canceledOrderDto = orderService.cancelOrder(id);
            return new ResponseEntity<>(canceledOrderDto, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (OrderCancellationNotAllowedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
