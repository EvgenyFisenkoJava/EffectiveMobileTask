package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.dto.CompanyDto;
import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.Image;
import com.example.effectivemobiletask.service.AuthService;
import com.example.effectivemobiletask.service.CompanyService;
import com.example.effectivemobiletask.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final AuthService authService;
    private final ImageService imageService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable int id) {
        return ResponseEntity.ok(companyService.getCompany(id));
    }

    @PostMapping(value = "/me")
    public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto companyDto,
                                                 Authentication authentication) throws IOException {

        companyService.addCompany(authentication, companyDto);

        return ResponseEntity.ok().build();
    }
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<String> updateUserImage(@RequestParam MultipartFile image,
                                                   @PathVariable int id, Authentication authentication) throws IOException {
       imageService.uploadImage(id, image, authentication);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})

    public ResponseEntity<byte[]> getImage(@PathVariable("id") int companyId) throws IOException {
        return ResponseEntity.ok(imageService.getImage(companyId));
    }

}
