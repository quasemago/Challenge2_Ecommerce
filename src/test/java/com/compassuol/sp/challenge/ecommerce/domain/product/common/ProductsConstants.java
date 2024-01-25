package com.compassuol.sp.challenge.ecommerce.domain.product.common;

import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductsConstants {
    public static final Product VALID_PRODUCT = Product.builder()
            .name("Product Valid").description("Description Valid").value(BigDecimal.valueOf(10.0))
            .build();
    public static final Product INVALID_PRODUCT = Product.builder()
            .name("").description("short").value(BigDecimal.valueOf(-10.0))
            .build();
}
