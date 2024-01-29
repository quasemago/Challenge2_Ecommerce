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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod payment_method;
    @Column(name = "subtotal_value", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal subtotal_value;
    @Column(name = "discount", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal discount;
    @Column(name = "total_value", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal total_value;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime created_date;
    @Column(name = "update_date")
    private LocalDateTime update_date;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;
    @Column(name = "cancel_reason", length = 256)
    private String cancel_reason;
    @Column(name = "cancel_date")
    private LocalDateTime cancel_date;
}
