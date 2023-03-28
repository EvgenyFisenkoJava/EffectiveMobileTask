package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.CreateProductDto;
import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper
public interface ProductMapper {
@Mapping(source = "averageRating", target = "averageRating")
    ProductDto productToProductDto(Product product);
    @Mapping(source = "title", target = "title")
    Product createDtoToProduct(CreateProductDto createProductDto);



    Collection<ProductDto> productsToProductDtos(Collection<Product>discount);
}

