package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.CreateProductDto;
import com.example.effectivemobiletask.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreateProductDtoMapper {

    @Mapping(source = "title", target = "title")
    CreateProductDto productToProductDto(Product product);


}
