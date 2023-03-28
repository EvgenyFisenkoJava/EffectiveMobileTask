package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.dto.DiscountDto;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import com.example.effectivemobiletask.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discount")

public class DiscountController {
    private final DiscountService discountService;

    @PatchMapping(value = "/{companyId}/product/{productId}")
    public ResponseEntity<DiscountDto> updateDiscount(@PathVariable int companyId,
                                                      @PathVariable int productId,
                                                      @RequestBody DiscountDto discountDto,
                                                      Authentication authentication) throws NotAuthorizedException {
        discountService.updateDiscount(productId, discountDto, authentication);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{companyId}/product/{productId}")
    public ResponseEntity<String> setDiscount(@PathVariable int companyId,
                                              @PathVariable int productId,
                                              @RequestBody DiscountDto discountDto,
                                              Authentication authentication) throws NotAuthorizedException {
        discountService.setDiscount(productId, discountDto, authentication);
        return ResponseEntity.ok("Discount has been set");
    }

    @PostMapping(value = "/product/{keyWord}")
    public ResponseEntity<String> setGroupDiscount(
            @PathVariable String keyWord,
            @RequestBody DiscountDto discountDto,
            Authentication authentication) throws NotAuthorizedException {
        discountService.setGroupDiscount(keyWord, discountDto, authentication);
        return ResponseEntity.ok("Discount has been set");
    }
}
