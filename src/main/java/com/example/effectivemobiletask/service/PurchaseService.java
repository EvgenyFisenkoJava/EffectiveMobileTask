package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.dto.FeedbackDto;
import com.example.effectivemobiletask.dto.PurchaseDto;
import com.example.effectivemobiletask.exceptions.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PurchaseService {
    void buyProduct(int productId,int wantedQuantity, Authentication authentication) throws NoSuchQuantityException, BalanceIsnotEnoughException, BannedException;
    void returnProduct(int purchaseId, Authentication authentication) throws ReturnPeriodExpiredException;
    List<PurchaseDto> getHistory(Authentication authentication);
    List<PurchaseDto> getAnyHistory(int userId,Authentication authentication) throws NotAuthorizedException;
    FeedbackDto addFeedback(int productId, FeedbackDto feedbackDto, Authentication authentication) throws NotAuthorizedException;
List<FeedbackDto>getFeedbacks(int productId);
}
