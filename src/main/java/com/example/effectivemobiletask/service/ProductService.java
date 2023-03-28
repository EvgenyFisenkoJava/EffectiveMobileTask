package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.dto.CreateProductDto;
import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.ProductFeatureDto;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import org.springframework.security.core.Authentication;

public interface ProductService {
    CreateProductDto addProduct(CreateProductDto createProductDto, Authentication authentication, int companyId) throws NotAuthorizedException;

    void removeProduct(int productId, Authentication authentication) throws NotAuthorizedException;

    void setStatus(int productId, Authentication authentication) throws NotAuthorizedException;

    ProductDto getProduct(int productId);

    CreateProductDto updateProduct(int productId, CreateProductDto createProductDto, Authentication authentication) throws NotAuthorizedException;

    ProductFeatureDto addFeature(int productId, ProductFeatureDto productFeatureDto, Authentication authentication) throws NotAuthorizedException;
}
