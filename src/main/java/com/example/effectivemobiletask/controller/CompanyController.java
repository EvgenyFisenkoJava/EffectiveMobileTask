package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.dto.CompanyDto;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import com.example.effectivemobiletask.service.CompanyService;
import com.example.effectivemobiletask.service.ImageService;
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
    private final ImageService imageService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable int id) {
        return ResponseEntity.ok(companyService.getCompany(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto companyDto,
                                                 Authentication authentication) throws IOException {

        companyService.addCompany(authentication, companyDto);

        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<String> updateUserImage(@RequestParam MultipartFile image,
                                                  @PathVariable int id, Authentication authentication) throws IOException, NotAuthorizedException {
        imageService.uploadImage(id, image, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/image", produces = {MediaType.IMAGE_PNG_VALUE})

    public ResponseEntity<byte[]> getImage(@PathVariable("id") int companyId) throws IOException {
        return ResponseEntity.ok(imageService.getImage(companyId));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CompanyDto> removeCompany(@PathVariable int id, Authentication authentication) {
        companyService.removeCompany(authentication, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<String> setStatus(@PathVariable("id") int companyId,
                                            Authentication authentication) throws NotAuthorizedException {
        companyService.setStatus(companyId, authentication);
        return ResponseEntity.ok("Status has been changed");
    }
}
