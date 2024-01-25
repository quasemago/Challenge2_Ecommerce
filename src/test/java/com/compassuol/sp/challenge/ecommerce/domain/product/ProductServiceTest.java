
package com.compassuol.sp.challenge.ecommerce.domain.product;

import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.domain.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.domain.product.common.ProductsConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void create_WithValidData_ReturnsProduct(){
        when(productRepository.save(VALID_PRODUCT)).thenReturn(VALID_PRODUCT);
        Product sut = productService.create(VALID_PRODUCT);
        assertThat(sut).isEqualTo(VALID_PRODUCT);
    }
    @Test
    public void create_WithInvalidData_ThrowsException(){
        when(productRepository.save(INVALID_PRODUCT)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> productService.create(INVALID_PRODUCT)).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void getProductById_WithExistingId_ReturnsProduct(){
        when(productRepository.findById(1L)).thenReturn(Optional.of(PRODUCT_1));
        Product sut = productService.getProductById(1L);
        assertThat(sut).isEqualTo(PRODUCT_1);
    }
    @Test
    public void getProductById_WithUnexistingId_EntityNotFoundException(){
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
       assertThatThrownBy(() ->  productService.getProductById(1L)).isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    public void getAllProducts_ReturnAllProducts(){
        List<Product> mockProducts = Arrays.asList(PRODUCT_1, PRODUCT_2);
        when(productRepository.findAll()).thenReturn(mockProducts);
        List<Product> sut = productService.getAllProducts();
        assertThat(sut).isNotNull();
        assertThat(sut).hasSize(2);
        assertThat(sut).containsExactlyInAnyOrder(PRODUCT_1, PRODUCT_2);
        verify(productRepository).findAll();

    }
    @Test
    public void getAllProducts_ReturnNoProducts(){
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<Product> sut = productService.getAllProducts();
        assertThat(sut).isEmpty();
        verify(productRepository).findAll();

    }


}