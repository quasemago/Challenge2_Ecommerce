package com.compassuol.sp.challenge.ecommerce.domain.product.service;

import com.compassuol.sp.challenge.ecommerce.domain.product.exception.UniqueProductViolationException;
import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductCreateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product create(Product product) {
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueProductViolationException("Já existe um produto cadastrado com esse nome.");
        }
    }

    @Transactional
    public Product update(ProductCreateDto dto, Long id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Produto não encontrado")
        );
        existingProduct.setName(dto.getName());
        existingProduct.setValue(dto.getValue());
        existingProduct.setDescription(dto.getDescription());
        return productRepository.save(existingProduct);

    }

    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Não existe o produto com o Id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Nenhum produto foi encontrado com este Id: " + id)
        );
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
