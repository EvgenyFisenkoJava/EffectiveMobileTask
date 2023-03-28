package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper
public interface ProductMapper {
@Mapping(source = "averageRating", target = "averageRating")
    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);


    Collection<ProductDto> productsToProductDtos(Collection<Product>discount);
}

