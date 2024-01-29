package com.compassuol.sp.challenge.ecommerce.domain.order.service;

import com.compassuol.sp.challenge.ecommerce.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
}
