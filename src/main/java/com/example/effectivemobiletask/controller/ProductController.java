package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.dto.CreateProductDto;
import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.ProductFeatureDto;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
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

    @PostMapping("/{id}/products")
    public ResponseEntity<CreateProductDto> addProduct(@PathVariable("id") int companyId,
                                                       @RequestBody CreateProductDto createProductDto,
                                                       Authentication authentication) throws NotAuthorizedException {
        productService.addProduct(createProductDto, authentication, companyId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") int productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<ProductDto> removeProduct(@PathVariable("id") int productId,
                                                    Authentication authentication) throws NotAuthorizedException {
        productService.removeProduct(productId, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/products/{id}/status")
    public ResponseEntity<String> setStatus(@PathVariable("id") int productId,
                                            Authentication authentication) throws NotAuthorizedException {
        productService.setStatus(productId, authentication);
        return ResponseEntity.ok("Status has been changed");
    }

    @PostMapping(value = "/products/{id}/feature")
    public ResponseEntity<ProductFeatureDto> addFeature(@PathVariable("id") int productId,
                                                        @RequestBody ProductFeatureDto productFeatureDto,
                                                        Authentication authentication) throws NotAuthorizedException {
        productService.addFeature(productId, productFeatureDto, authentication);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/products/{id}")
    public ResponseEntity<CreateProductDto> updateProduct(@PathVariable("id") int productId,
                                                          @RequestBody CreateProductDto createProductDto,
                                                          Authentication authentication) throws NotAuthorizedException {
        return ResponseEntity.ok(productService.updateProduct(productId, createProductDto, authentication));
    }

}
