package com.compassuol.sp.challenge.ecommerce.domain.order.model;

import com.compassuol.sp.challenge.ecommerce.domain.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.domain.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderProduct> products;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
    @Column(name = "subtotal_value", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal subtotalValue;
    @Column(name = "discount", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal discount;
    @Column(name = "total_value", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal totalValue;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;
    @Column(name = "cancel_reason", length = 256)
    private String cancelReason;
    @Column(name = "cancel_date")
    private LocalDateTime cancelDate;
}
