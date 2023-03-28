package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.ProductFeatureDto;
import com.example.effectivemobiletask.model.ProductFeature;
import com.example.effectivemobiletask.service.AuthService;
import com.example.effectivemobiletask.service.CompanyService;
import com.example.effectivemobiletask.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CompanyService companyService;
    private final AuthService authService;


    @PostMapping("/{id}/products")
    public ResponseEntity<ProductDto> addProduct(@PathVariable int id,
                                                 @RequestBody ProductDto productDto,
                                                 Authentication authentication) {
        productService.addProduct(productDto, authentication, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @DeleteMapping(value = "/me/product/{productId}")
    public ResponseEntity<ProductDto> removeProduct(@PathVariable int productId,
                                                    Authentication authentication) {
        productService.removeProduct(productId, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/me/{productId}")
    public ResponseEntity<String> setStatus(@PathVariable int productId,
                                            Authentication authentication) {
        productService.setStatus(productId, authentication);
        return ResponseEntity.ok("Status has been changed");
    }
    @PostMapping(value = "/me/{productId}/feature")
    public ResponseEntity<ProductFeatureDto> addFeature(@PathVariable int productId,
                                                     @RequestBody ProductFeatureDto productFeatureDto,
                                                     Authentication authentication) {
        productService.addFeature(productId, productFeatureDto, authentication );

        return ResponseEntity.ok().build();
    }

}
