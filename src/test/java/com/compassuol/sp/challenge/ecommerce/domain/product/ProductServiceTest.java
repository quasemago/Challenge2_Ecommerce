
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
    public void getProductById_WithUnexistingId_ThrowsEntityNotFoundException(){
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
    @Test
    public void deleteProduct_WithExistingId_doesNotThrowAnyException(){
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        assertThatCode(() -> productService.deleteProduct(productId)).doesNotThrowAnyException();

        verify(productRepository).existsById(productId);
        verify(productRepository).deleteById(productId);

    }
    @Test
    public void deleteProduct_ProductDoesNotExist_ThrowsEntityNotFoundException() {

        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(productId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Não existe o produto com o Id: " + productId);

        verify(productRepository).existsById(productId);
        verify(productRepository, never()).deleteById(productId);
    }

    @Test
    public void updateProduct_WithExistingId_ReturnsUpdatedProduct() {

        Long productId = 3L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(EXISTING_PRODUCT));
        when(productRepository.save(EXISTING_PRODUCT)).thenReturn(UPDATED_PRODUCT);

        Product sut = productService.update(UPDATED_PRODUCT, productId);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(UPDATED_PRODUCT);
        assertThat(EXISTING_PRODUCT.getName()).isEqualTo("New Name");
        assertThat(EXISTING_PRODUCT.getValue()).isEqualTo(new BigDecimal(20.0));
        assertThat(EXISTING_PRODUCT.getDescription()).isEqualTo("New Description");

        verify(productRepository).findById(productId);
        verify(productRepository).save(EXISTING_PRODUCT);
    }

    @Test
    public void updateProduct_ProductDoesNotExist_ThrowsEntityNotFoundException() {

        Long productId = 3L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> productService.update(UPDATED_PRODUCT, productId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Não existe o produto com o Id: " + productId);

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }
}