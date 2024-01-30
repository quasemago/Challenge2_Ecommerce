package com.compassuol.sp.challenge.ecommerce.domain.order.repository;

import com.compassuol.sp.challenge.ecommerce.domain.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByStatusOrderByCreatedDateDesc(OrderStatus status);

    @Query("SELECT o FROM Order o ORDER BY o.createdDate DESC")
    List<Order> findAllOrderByCreatedDateDesc();
}
