package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.dto.ProductDto;
import org.springframework.security.core.Authentication;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto, Authentication authentication, int companyId);

    void removeProduct(int productId, Authentication authentication);

    void setStatus(int productId, Authentication authentication);

    ProductDto getProduct(int productId);
    ProductDto buyProduct(int productId,int wantedQuantity, Authentication authentication);

}
