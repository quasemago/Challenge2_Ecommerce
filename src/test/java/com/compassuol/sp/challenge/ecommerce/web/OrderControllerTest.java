package com.compassuol.sp.challenge.ecommerce.web;

import com.compassuol.sp.challenge.ecommerce.domain.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OpenFeignBadRequestException;
import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OpenFeignNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.web.controller.OrderController;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderResponseDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.mapper.OrderMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.compassuol.sp.challenge.ecommerce.common.OrderUtils.*;
import static com.compassuol.sp.challenge.ecommerce.domain.order.enums.OrderStatus.CONFIRMED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    public void updateOrder_WithValidData_ReturnsUpdatedOrderDto() throws Exception {
        Long orderId = 1L;
        when(orderService.updateOrder(any(Order.class), eq(orderId))).thenReturn(generateValidOrder(CONFIRMED));
        final OrderResponseDto responseBody = toResponseDto(generateValidOrder(CONFIRMED));

        mockMvc.perform(put("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUpdateDto(generateValidOrder(CONFIRMED)))))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.status").value(responseBody.getStatus())
                );
        verify(orderService, times(1)).updateOrder(any(Order.class), eq(orderId));
    }

    @Test
    public void updateOrder_WithNonExistingId_ReturnsNotFound() throws Exception {
        Long orderId = 1L;
        doThrow(EntityNotFoundException.class).when(orderService).updateOrder(any(Order.class), eq(orderId));

        mockMvc.perform(put("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUpdateDto(generateValidOrder(CONFIRMED)))))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).updateOrder(any(Order.class), eq(orderId));

    }
}
