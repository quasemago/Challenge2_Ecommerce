package com.compassuol.sp.challenge.ecommerce.domain.order.service;

import com.compassuol.sp.challenge.ecommerce.domain.order.consumer.AddressConsumerFeign;
import com.compassuol.sp.challenge.ecommerce.domain.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.domain.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OpenFeignNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.order.exception.OrderCancellationNotAllowedException;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Address;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.OrderProduct;
import com.compassuol.sp.challenge.ecommerce.domain.order.repository.OrderRepository;
import com.compassuol.sp.challenge.ecommerce.domain.product.service.ProductService;
import com.compassuol.sp.challenge.ecommerce.web.dto.AddressCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.OrderCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProductOrderDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final AddressConsumerFeign addressConsumerFeign;

    private OrderProduct createOrderProduct(Order order, ProductOrderDto productDto) {
        final OrderProduct product = new OrderProduct();
        product.setOrder(order);
        product.setProduct(productService.getProductById(productDto.getProductId()));
        product.setQuantity(productDto.getQuantity());
        return product;
    }

    private Address createOrderAddress(AddressCreateDto addressDto) {
        final Address address = addressConsumerFeign.getAddressByCep(addressDto.getPostalCode());
        if (address.getPostalCode() == null) {
            throw new OpenFeignNotFoundException("Nenhum endereço foi encontrado com este CEP: " + addressDto.getPostalCode());
        }
        address.setNumber(addressDto.getNumber());
        address.setComplement(addressDto.getComplement());
        return address;
    }

    @Transactional
    public Order create(OrderCreateDto createDto) {
        final Order order = new Order();

        order.setProducts(createDto.getProducts().stream()
                .map(x -> createOrderProduct(order, x))
                .collect(Collectors.toList()));

        order.setAddress(createOrderAddress(createDto.getAddress()));
        order.setPaymentMethod(PaymentMethod.valueOf(createDto.getPaymentMethod()));

        final BigDecimal subtotal = order.getProducts().stream()
                .map(product -> product.getProduct().getValue().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setSubtotalValue(subtotal);

        if (createDto.getPaymentMethod().equals(PaymentMethod.PIX.name())) {
            order.setDiscount(subtotal.multiply(BigDecimal.valueOf(0.05)));
        } else {
            order.setDiscount(BigDecimal.ZERO);
        }

        order.setTotalValue(subtotal.subtract(order.getDiscount()));

        order.setCreatedDate(LocalDateTime.now(ZoneOffset.UTC));
        order.setStatus(OrderStatus.CONFIRMED);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Nenhum pedido foi encontrado com este Id: " + id)
        );
    }

    @Transactional
    public List<Order> getAllByStatus(OrderStatus status) {
        if (status == null) {
            return orderRepository.findAllOrderByCreatedDateDesc();
        } else {
            return orderRepository.findAllByStatusOrderByCreatedDateDesc(status);
        }
    }

    @Transactional
    public Order updateOrder(Order order, Long id) {
        Order existingOrder = getOrderById(id);
        existingOrder.setStatus(order.getStatus());
        existingOrder.setUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
        return orderRepository.save(existingOrder);
    }

    @Transactional
    public Order cancelOrder(Long orderId, String cancelReason) {
        Order order = getOrderById(orderId);

        if (order.getStatus().equals(OrderStatus.CANCELED)) {
            throw new OrderCancellationNotAllowedException("Pedido já foi cancelado");
        }

        if (order.getStatus().equals(OrderStatus.SENT)) {
            throw new OrderCancellationNotAllowedException("Pedido não pode ser cancelado, pois já foi enviado.");
        }

        if (!canCancelOrder(order)) {
            throw new OrderCancellationNotAllowedException("Pedido não pode ser cancelado, pois já se passou 90 dias.");
        }

        order.setStatus(OrderStatus.CANCELED);
        order.setCancelDate(LocalDateTime.now(ZoneOffset.UTC));
        order.setCancelReason(cancelReason);
        return orderRepository.save(order);
    }

    private boolean canCancelOrder(Order order) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime orderCreatedDate = order.getCreatedDate();

        return Duration.between(orderCreatedDate, now).toDays() <= 90;
    }
}
