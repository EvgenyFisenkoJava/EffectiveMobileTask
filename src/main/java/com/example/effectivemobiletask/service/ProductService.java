package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.ProductFeatureDto;
import com.example.effectivemobiletask.model.ProductFeature;
import org.springframework.security.core.Authentication;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto, Authentication authentication, int companyId);

    void removeProduct(int productId, Authentication authentication);

    void setStatus(int productId, Authentication authentication);

    ProductDto getProduct(int productId);
    ProductFeatureDto addFeature(int productId, ProductFeatureDto productFeatureDto, Authentication authentication);
}
