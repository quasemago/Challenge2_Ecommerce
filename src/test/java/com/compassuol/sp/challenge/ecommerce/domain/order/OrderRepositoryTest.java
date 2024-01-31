package com.compassuol.sp.challenge.ecommerce.domain.order;

import com.compassuol.sp.challenge.ecommerce.domain.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.domain.order.model.Order;
import com.compassuol.sp.challenge.ecommerce.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static com.compassuol.sp.challenge.ecommerce.common.OrderUtils.generateInvalidOrder;
import static com.compassuol.sp.challenge.ecommerce.common.OrderUtils.generateValidOrder;
import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.VALID_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DataJpaTest
public class OrderRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void createOrder_WithValidData_ReturnsOrder() {
        final Order validOrder = generateValidOrder(PaymentMethod.CREDIT_CARD, VALID_PRODUCT);

        final Order savedOrder = orderRepository.save(validOrder);
        final Order sutOrder = testEntityManager.find(Order.class, savedOrder.getId());

        assertThat(sutOrder).isNotNull();
        assertThat(sutOrder.getProducts()).isEqualTo(validOrder.getProducts());
        assertThat(sutOrder.getAddress()).isEqualTo(validOrder.getAddress());
        assertThat(sutOrder.getPaymentMethod()).isEqualTo(validOrder.getPaymentMethod());
    }

    @Test
    public void createOrder_WithInvalidData_ThrowsException() {
        final Order sutOrder = generateInvalidOrder();

        assertThatThrownBy(() -> orderRepository.save(sutOrder))
                .isInstanceOf(Exception.class);
    }
}
