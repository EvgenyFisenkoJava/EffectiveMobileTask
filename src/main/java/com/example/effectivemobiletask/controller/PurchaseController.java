package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.dto.FeedbackDto;
import com.example.effectivemobiletask.dto.PurchaseDto;
import com.example.effectivemobiletask.exceptions.*;
import com.example.effectivemobiletask.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PatchMapping(value = "/{id}/{qty}")
    public ResponseEntity<String> buyProduct(@PathVariable("id") int productId,
                                             @PathVariable int qty,
                                             Authentication authentication) throws NoSuchQuantityException, BalanceIsnotEnoughException, BannedException {
        purchaseService.buyProduct(productId, qty, authentication);
        return ResponseEntity.ok("product is purchased");
    }

    @PatchMapping(value = "/return/{id}")
    public ResponseEntity<String> setStatus(@PathVariable("id") int purchaseId,
                                            Authentication authentication) throws ReturnPeriodExpiredException {
        purchaseService.returnProduct(purchaseId, authentication);
        return ResponseEntity.ok("product is returned");
    }

    @GetMapping(value = "")
    public ResponseEntity<List<PurchaseDto>> getPurchaseHistory(Authentication authentication) {
        return ResponseEntity.ok(purchaseService.getHistory(authentication));
    }

    @GetMapping(value = "/history/{id}")
    public ResponseEntity<List<PurchaseDto>> getAnyPurchaseHistory(@PathVariable("id") int userId,
                                                                   Authentication authentication) throws NotAuthorizedException {

        return ResponseEntity.ok(purchaseService.getAnyHistory(userId, authentication));
    }

    @PostMapping(value = "/{id}/feedacks")
    public ResponseEntity<FeedbackDto> addFeedback(@PathVariable("id") int productId,
                                                   @RequestBody FeedbackDto feedbackDto,
                                                   Authentication authentication) throws NotAuthorizedException {
        purchaseService.addFeedback(productId, feedbackDto, authentication);
        return ResponseEntity.ok(feedbackDto);
    }

    @GetMapping(value = "/{id}/feedbacks")
    public ResponseEntity<List<FeedbackDto>> getFeedbacks(
            @PathVariable("id") int productId) {
        return ResponseEntity.ok(purchaseService.getFeedbacks(productId));
    }
}
