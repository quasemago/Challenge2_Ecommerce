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

    public static final Product PRODUCT_1 = new Product(1L, "Product 1", BigDecimal.valueOf(10.0), "Description 1");
    public static final Product PRODUCT_2 = new Product(2L, "Product 2", BigDecimal.valueOf(20.0), "Description 2");
    public static final Product EXISTING_PRODUCT= new Product(3L, "Old Name", BigDecimal.valueOf(10.0), "Old Description");
    public static final Product UPDATED_PRODUCT= new Product(3L, "New Name",BigDecimal.valueOf(20.0), "New Description");

}
