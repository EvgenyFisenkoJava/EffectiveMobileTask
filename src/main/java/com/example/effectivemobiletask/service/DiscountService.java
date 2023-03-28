package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.dto.DiscountDto;
import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public interface DiscountService {
    DiscountDto updateDiscount(int productId, DiscountDto discountDto, Authentication authentication) throws NotAuthorizedException;

    void removeExpiredDiscount();

    ProductDto setDiscount(int productId, DiscountDto discountDto, Authentication authentication) throws NotAuthorizedException;

    Collection<ProductDto> setGroupDiscount(String keyWord, DiscountDto discountDto, Authentication authentication) throws NotAuthorizedException;
}
