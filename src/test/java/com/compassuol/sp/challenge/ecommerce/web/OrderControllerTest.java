package com.compassuol.sp.challenge.ecommerce.web;

import com.compassuol.sp.challenge.ecommerce.domain.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.domain.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OpenFeignBadRequestException;
import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OpenFeignNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.web.controller.OrderController;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.OrderMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.compassuol.sp.challenge.ecommerce.common.OrderUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;

    @Test
    public void createOrder_WithValidData_ReturnsOrder() throws Exception {
        final Order sutOrder = generateValidOrder(PaymentMethod.CREDIT_CARD);

        when(orderService.create(any())).thenReturn(sutOrder);
        final OrderResponseDto responseBody = OrderMapper.toDto(sutOrder);

        mockMvc.perform(
                        post("/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createOrderDto(sutOrder)))
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBody)));

        verify(orderService, times(1)).create(any());
    }

    @Test
    public void createOrder_WithInvalidData_ReturnsBadRequest() throws Exception {
        final Order sutOrder = generateInvalidOrder();

        mockMvc.perform(
                        post("/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createOrderDto(sutOrder)))
                )
                .andExpect(status().isBadRequest());

        verify(orderService, never()).create(any());
    }

    @Test
    public void createOrder_WithInvalidAddress_ReturnsBadRequest() throws Exception {
        when(orderService.create(any())).thenThrow(OpenFeignBadRequestException.class);

        final Order sutOrder = generateValidOrder(PaymentMethod.CREDIT_CARD);
        sutOrder.getAddress().setPostalCode("010000000");

        mockMvc.perform(
                        post("/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createOrderDto(sutOrder)))
                )
                .andExpect(status().isBadRequest());

        verify(orderService, times(1)).create(any());
    }

    @Test
    public void createOrder_WithNonExistingAddress_ReturnsBadRequest() throws Exception {
        when(orderService.create(any())).thenThrow(OpenFeignNotFoundException.class);

        final Order sutOrder = generateValidOrder(PaymentMethod.CREDIT_CARD);
        sutOrder.getAddress().setPostalCode("00000000");

        mockMvc.perform(
                        post("/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createOrderDto(sutOrder)))
                )
                .andExpect(status().isBadRequest());

        verify(orderService, times(1)).create(any());
    }

    @Test
    public void getAllOrders_ReturnsListOfOrders() throws Exception {
        final List<Order> orders = List.of(generateValidOrder(PaymentMethod.CREDIT_CARD));

        when(orderService.getAllByStatus(null)).thenReturn(orders);

        List<OrderResponseDto> responseBody = OrderMapper.toDtoList(orders);
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBody)));
    }

    @Test
    public void getAllOrders_ReturnsEmptyList() throws Exception {
        when(orderService.getAllByStatus(OrderStatus.CANCELED)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    public void getAllOrders_WithStatusFilter_ReturnsOrdersList() throws Exception {
        final List<Order> orders = List.of(generateValidOrder(PaymentMethod.CREDIT_CARD));
        when(orderService.getAllByStatus(OrderStatus.SENT)).thenReturn(orders);

        final List<OrderResponseDto> responseBody = OrderMapper.toDtoList(orders);

        mockMvc.perform(
                        get("/orders?status=SENT"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseBody)));

        verify(orderService, times(1)).getAllByStatus(OrderStatus.SENT);
    }
}