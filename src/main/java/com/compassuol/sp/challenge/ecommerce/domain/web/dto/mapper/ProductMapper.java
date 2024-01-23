package com.compassuol.sp.challenge.ecommerce.domain.web.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.domain.product.model.Product;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductCreateDto;
import com.compassuol.sp.challenge.ecommerce.domain.web.dto.ProductResponseDto;

import org.modelmapper.ModelMapper;


public class ProductMapper {
    public static Product toProduct (ProductCreateDto dto){
        return new ModelMapper().map(dto, Product.class);
    }

    public static ProductResponseDto toDto (Product product){
        return new ModelMapper().map(product, ProductResponseDto.class);
    }
}
