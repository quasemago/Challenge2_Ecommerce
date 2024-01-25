package com.compassuol.sp.challenge.ecommerce.domain.product;


import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.compassuol.sp.challenge.ecommerce.domain.product.common.ProductsConstants.VALID_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void afterEach() {
        VALID_PRODUCT.setId(null);
    }

    @Test
    public void createProduct_WithValidData_ReturnsProduct() {
        Product savedProduct = productRepository.save(VALID_PRODUCT);

        Product sutProduct = testEntityManager.find(Product.class, savedProduct.getId());

        assertThat(sutProduct).isNotNull();
        assertThat(sutProduct.getName()).isEqualTo(VALID_PRODUCT.getName());
        assertThat(sutProduct.getDescription()).isEqualTo(VALID_PRODUCT.getDescription());
        assertThat(sutProduct.getValue()).isEqualTo(VALID_PRODUCT.getValue());
    }

    private static Stream<Arguments> providesInvalidProducts() {
        return Stream.of(
                Arguments.of(Product.builder().name("").description("Description Valid").value(BigDecimal.valueOf(10.0)).build()),
                Arguments.of(Product.builder().name("Product Valid").description("").value(BigDecimal.valueOf(10.0)).build()),
                Arguments.of(Product.builder().name("Product Valid").description("Description Valid").value(BigDecimal.valueOf(-10.0)).build())
        );
    }

    @ParameterizedTest
    @MethodSource("providesInvalidProducts")
    public void createProduct_WithInvalidData_ReturnsProduct(Product product) {
        assertThatThrownBy(() -> productRepository.save(product))
                .isInstanceOf(Exception.class);
    }

    @Test
    public void createProduct_WithExistingName_ThrowsException() {
        final Product product = testEntityManager.persistFlushFind(VALID_PRODUCT);
        testEntityManager.detach(product);
        product.setId(null);

        assertThatThrownBy(() -> productRepository.save(product))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void getProductById_WithExistingId_ReturnsProduct() {
        Product product = testEntityManager.persistFlushFind(VALID_PRODUCT);

        Optional<Product> productOpt = productRepository.findById(product.getId());

        assertThat(productOpt).isNotEmpty();
        assertThat(productOpt.get()).isEqualTo(product);
    }

    @Test
    public void getProductById_WithNonExistingId_ReturnsEmpty() {
        Optional<Product> productOpt = productRepository.findById(1L);

        assertThat(productOpt).isEmpty();
    }

    @Test
    public void deleteProduct_WithExistingId_DeleteProductFromDataBase() {
        Product product = testEntityManager.persistFlushFind(VALID_PRODUCT);

        productRepository.deleteById(product.getId());

        Product removedProduct = testEntityManager.find(Product.class, product.getId());
        assertThat(removedProduct).isNull();
    }

    @Sql("/sql/products/insert_products.sql")
    @Test
    public void getAllProducts_ReturnsProductsList() {
        final List<Product> productList = productRepository.findAll();

        assertThat(productList).isNotEmpty();
        assertThat(productList).hasSize(3);
        assertThat(productList.get(0).getName()).isEqualTo("Apple");
    }

    @Test
    public void getAllProducts_ReturnsEmptyList() {
        final List<Product> productList = productRepository.findAll();
        assertThat(productList).isEmpty();
    }
}