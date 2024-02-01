package com.compassuol.sp.challenge.ecommerce.domain.order;

import com.compassuol.sp.challenge.ecommerce.domain.order.consumer.AddressConsumerFeign;
import com.compassuol.sp.challenge.ecommerce.domain.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.domain.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OpenFeignBadRequestException;
import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OpenFeignNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Address;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.repository.OrderRepository;
import com.compassuol.sp.challenge.ecommerce.domain.order.service.OrderService;
import com.compassuol.sp.challenge.ecommerce.domain.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.OrderUtils.*;
import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private AddressConsumerFeign addressConsumerFeign;

    @Test
    public void createOrder_WithValidData_ReturnsOrder() {
        final Order validOrder = generateValidOrder(PaymentMethod.CREDIT_CARD);

        when(orderRepository.save(any(Order.class))).thenReturn(validOrder);
        when(productService.getProductById(anyLong())).thenReturn(validOrder.getProducts().get(0).getProduct());
        when(addressConsumerFeign.getAddressByCep(anyString())).thenReturn(validOrder.getAddress());

        Order sut = orderService.create(createOrderDto(validOrder));

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(validOrder);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(productService, times(1)).getProductById(anyLong());
        verify(addressConsumerFeign, times(1)).getAddressByCep(anyString());
    }

    @Test
    public void createOrder_WithValidDataAndDiscount_ReturnsOrder() {
        final Order validOrder = generateValidOrder(PaymentMethod.PIX);

        when(orderRepository.save(any(Order.class))).thenReturn(validOrder);
        when(productService.getProductById(anyLong())).thenReturn(validOrder.getProducts().get(0).getProduct());
        when(addressConsumerFeign.getAddressByCep(anyString())).thenReturn(validOrder.getAddress());

        Order sut = orderService.create(createOrderDto(validOrder));

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(validOrder);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(productService, times(1)).getProductById(anyLong());
        verify(addressConsumerFeign, times(1)).getAddressByCep(anyString());
    }

    @Test
    public void createOrder_WithInvalidData_ThrowsException() {
        final Order sutOrder = generateInvalidOrder();

        assertThatThrownBy(() -> orderService.create(createOrderDto(sutOrder)))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createOrder_WithInvalidAddress_ThrowsException() {
        final Order sutOrder = generateValidOrder(PaymentMethod.CREDIT_CARD);
        sutOrder.getAddress().setPostalCode("010010002");

        when(productService.getProductById(anyLong())).thenReturn(sutOrder.getProducts().get(0).getProduct());
        when(addressConsumerFeign.getAddressByCep("010010002")).thenThrow(OpenFeignBadRequestException.class);

        assertThatThrownBy(() -> orderService.create(createOrderDto(sutOrder)))
                .isInstanceOf(OpenFeignBadRequestException.class);

        verify(productService, times(1)).getProductById(anyLong());
        verify(addressConsumerFeign, times(1)).getAddressByCep(anyString());
    }

    @Test
    public void createOrder_WithNonExistingAddress_ThrowsException() {
        final Order sutOrder = generateValidOrder(PaymentMethod.CREDIT_CARD);
        sutOrder.getAddress().setPostalCode("00000000");

        when(productService.getProductById(anyLong())).thenReturn(sutOrder.getProducts().get(0).getProduct());
        when(addressConsumerFeign.getAddressByCep("00000000")).thenReturn(Address.builder().postalCode(null).build());

        assertThatThrownBy(() -> orderService.create(createOrderDto(sutOrder)))
                .isInstanceOf(OpenFeignNotFoundException.class);

        verify(productService, times(1)).getProductById(anyLong());
        verify(addressConsumerFeign, times(1)).getAddressByCep(anyString());
    }

    @Test
    public void getOrderById_WithExistingId_ReturnsOrder() {
        Order order = generateValidOrder(PaymentMethod.PIX, PRODUCT_1);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order sut = orderService.getOrderById(1L);
        assertThat(sut).isEqualTo(order);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void getOrderById_WithNonExistingId_ThrowsEntityNotFoundException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.getOrderById(1L)).isInstanceOf(EntityNotFoundException.class);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void getAllOrders_WithStatus_ReturnsOrderList() {
        List<Order> order = List.of(generateValidOrder(PaymentMethod.CREDIT_CARD), generateValidOrder(PaymentMethod.PIX));

        when(orderRepository.findAllByStatusOrderByCreatedDateDesc(OrderStatus.CONFIRMED))
                .thenReturn(order);

        List<Order> orderList = orderService.getAllByStatus(OrderStatus.CONFIRMED);

        assertThat(orderList).isNotNull();
        assertThat(orderList).hasSize(2);

        verify(orderRepository, times(1)).findAllByStatusOrderByCreatedDateDesc(OrderStatus.CONFIRMED);
    }

    @Test
    public void getAllOrders_WithoutStatus_ReturnsOrderList() {
        List<Order> order = List.of(generateValidOrder(PaymentMethod.CREDIT_CARD), generateValidOrder(PaymentMethod.PIX));

        when(orderRepository.findAllOrderByCreatedDateDesc())
                .thenReturn(order);

        List<Order> orderList = orderService.getAllByStatus(null);

        assertThat(orderList).isNotNull();
        assertThat(orderList).hasSize(2);

        verify(orderRepository, times(1)).findAllOrderByCreatedDateDesc();
    }
}
