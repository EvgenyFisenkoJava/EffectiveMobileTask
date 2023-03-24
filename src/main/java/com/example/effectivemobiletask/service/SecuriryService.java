package com.example.effectivemobiletask.service;

import org.springframework.security.core.Authentication;

public interface SecuriryService {
    boolean accessCompany(Authentication authentication, Long companyId);

    boolean accessProduct(Authentication authentication, Long productId);

    boolean role(Authentication authentication);
}
