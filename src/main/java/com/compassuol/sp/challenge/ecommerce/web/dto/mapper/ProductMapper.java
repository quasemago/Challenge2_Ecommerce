package com.compassuol.sp.challenge.ecommerce.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProductCreateDto;
import com.compassuol.sp.challenge.ecommerce.web.dto.ProductResponseDto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {
    public static Product toProduct(ProductCreateDto dto){
        return new ModelMapper().map(dto, Product.class);
    }

    public static ProductResponseDto toDto(Product product){
        return new ModelMapper().map(product, ProductResponseDto.class);
    }

    public static List<ProductResponseDto> toDtoList(List<Product> products) {
        return products.stream()
            .map(ProductMapper::toDto)
            .collect(Collectors.toList());
    }
}
