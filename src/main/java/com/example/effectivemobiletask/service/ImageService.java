package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void uploadImage(int companyId, MultipartFile image, Authentication authentication) throws IOException, NotAuthorizedException;

    byte[] getImage(int companyId);
}
