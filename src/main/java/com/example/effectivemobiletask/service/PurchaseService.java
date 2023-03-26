package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.dto.PurchaseDto;
import com.example.effectivemobiletask.model.Purchase;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.List;

public interface PurchaseService {
    void buyProduct(int productId,int wantedQuantity, Authentication authentication);
    void returnProduct(int productId, Authentication authentication);
    Collection<PurchaseDto> getHistory(Authentication authentication);
    Collection<PurchaseDto> getAnyHistory(int userId,Authentication authentication);

}
