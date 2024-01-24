package com.compassuol.sp.challenge.ecommerce.domain.product.service;

import com.compassuol.sp.challenge.ecommerce.domain.product.exception.ProductNotFoundException;
import com.compassuol.sp.challenge.ecommerce.domain.product.exception.UniqueProductViolationException;
import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductCreateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public Product getById(Long id){
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException()
        );

    }


    @Transactional
    public Product update(ProductCreateDto dto,Long productId) {
        try {
            Product existingProduct = getById(productId);
            existingProduct.setName(dto.getName());
            existingProduct.setValue(dto.getValue());
            existingProduct.setDescription(dto.getDescription());
            return productRepository.save(existingProduct);
        }
        catch (EntityNotFoundException e){
            throw new ProductNotFoundException("Produto não existe.");
        }
    }


}
