package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.ProductFeatureDto;
import com.example.effectivemobiletask.model.ProductFeature;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductFeatureMapper {
    ProductFeatureDto productFeatureToProductFeatureDto(ProductFeature productFeature);
List<ProductFeatureDto> prodListToProdDtoList(List<ProductFeature>productFeatureList);
}
