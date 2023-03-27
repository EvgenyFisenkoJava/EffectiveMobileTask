package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.dto.FeedbackDto;
import com.example.effectivemobiletask.dto.PurchaseDto;
import com.example.effectivemobiletask.service.AuthService;
import com.example.effectivemobiletask.service.ProductService;
import com.example.effectivemobiletask.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {
    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final AuthService authService;

    @PatchMapping(value = "/{productId}/{qty}")
    public ResponseEntity<String> buyProduct(@PathVariable int productId,
                                             @PathVariable int qty,
                                             Authentication authentication) {
        purchaseService.buyProduct(productId, qty, authentication);
        return ResponseEntity.ok("product is purchased");
    }

    @PatchMapping(value = "/return/{productId}")
    public ResponseEntity<String> setStatus(@PathVariable int productId,
                                            Authentication authentication) {
        purchaseService.returnProduct(productId, authentication);
        return ResponseEntity.ok("product is returned");
    }

    @GetMapping(value = "")
    public ResponseEntity<Collection<PurchaseDto>> getPurchaseHistory(Authentication authentication) {

        return ResponseEntity.ok(purchaseService.getHistory(authentication));
    }

    @GetMapping(value = "/admin/{userId}")
    public ResponseEntity<Collection<PurchaseDto>> getAnyPurchaseHistory(@PathVariable int userId,
                                                                         Authentication authentication) {

        return ResponseEntity.ok(purchaseService.getAnyHistory(userId, authentication));
    }
    @PostMapping(value = "/feedback/{productId}")
    public ResponseEntity<FeedbackDto> addFeedback(@PathVariable int productId,
                                                   @RequestBody FeedbackDto feedbackDto,
                                                   Authentication authentication){
        purchaseService.addFeedback(productId, feedbackDto, authentication);
        return ResponseEntity.ok(feedbackDto);
    }
    @GetMapping(value = "/{productId}/feedbacks")
    public ResponseEntity<List<FeedbackDto>> getFeedbacks(
            @PathVariable int productId){
       return ResponseEntity.ok(purchaseService.getFeedbacks(productId));

    }

}
