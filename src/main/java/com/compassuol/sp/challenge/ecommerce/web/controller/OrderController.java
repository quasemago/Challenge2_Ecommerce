package com.compassuol.sp.challenge.ecommerce.web.controller;

import com.compassuol.sp.challenge.ecommerce.domain.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderUpdateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProductResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.OrderMapper;
import com.compassuol.sp.challenge.ecommerce.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders API", description = "Contém as operações relativas ao domínio pedidos. " +
        "Permite que os usuários criem, leiam, atualizem e cancelem pedidos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Recuperar todos os pedidos existentes.", description = "Recurso para recuperar todos os pedidos existentes no banco de dados. " +
            "Requisição pode ser filtrada por status do pedido.",
            parameters = {
                    @Parameter(name = "status", description = "Status do pedido a ser filtrado.",
                            in = ParameterIn.QUERY, schema = @Schema(implementation = OrderStatus.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedidos encontrados com sucesso",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderResponseDto.class))))
            }
    )
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(@RequestParam(required = false) OrderStatus status) {
        final List<OrderResponseDto> orders = OrderMapper.toDtoList(orderService.getAllByStatus(status));
        return ResponseEntity.ok(orders);
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

    @Operation(summary = "Criar um novo pedido.", description = "Recurso para criar um novo pedido.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderCreateDto createDto) {
        final Order order = orderService.create(createDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderMapper.toDto(order));
    }

    @Operation(summary = "Atualizar um pedido existente.", description = "Recurso para atualizar as informações de um pedido existente através do Id.",
            parameters = {
                    @Parameter(name = "id", description = "Identificador (Id) do pedido no banco de dados.",
                            in = ParameterIn.PATH, required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Campo(s) mal formatado(s)",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderUpdateDto dto) {
        Order updatedOrder = orderService.updateOrder(OrderMapper.UpdateDtoToOrder(dto), id);
        return ResponseEntity.ok(OrderMapper.toDto(updatedOrder));
    }


    public ResponseEntity<Void> cancelOrder() {
        return null;
    }
}
