package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.dto.ProductDto;
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

    @GetMapping("/{id}/products/{productId}")
    public ResponseEntity<ProductDto> getCompany(@PathVariable int id,
                                                 @PathVariable int productId) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @DeleteMapping(value = "/{companyId}/product/{productId}")
    public ResponseEntity<ProductDto> removeProduct(@PathVariable int companyId,
                                                    @PathVariable int productId,
                                                    Authentication authentication) {
        productService.removeProduct(productId, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/{id}/product/{productId}")
    public ResponseEntity<String> setStatus(@PathVariable int id,
                                            @PathVariable int productId,
                                            Authentication authentication) {
        productService.setStatus(id, authentication);
        return ResponseEntity.ok("Status has been changed");
    }
    @PatchMapping(value = "/{companyId}/product/{productId}/{qty}")
    public ResponseEntity<String> setStatus(@PathVariable int companyId,
                                            @PathVariable int productId,
                                            @PathVariable int qty,
                                            Authentication authentication) {
        productService.buyProduct(productId, qty, authentication);
        return ResponseEntity.ok("Status has been changed");
    }
}
