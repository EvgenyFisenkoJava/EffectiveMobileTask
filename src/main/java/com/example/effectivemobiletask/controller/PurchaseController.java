package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.dto.PurchaseDto;
import com.example.effectivemobiletask.service.AuthService;
import com.example.effectivemobiletask.service.ProductService;
import com.example.effectivemobiletask.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {
    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final AuthService authService;

    @PatchMapping(value = "/{companyId}/product/{productId}/{qty}")
    public ResponseEntity<String> buyProduct(@PathVariable int companyId,
                                             @PathVariable int productId,
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
}
