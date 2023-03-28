package com.example.effectivemobiletask.service;

import org.springframework.security.core.Authentication;

public interface SecurityService {
    boolean accessUser(Authentication authentication, int companyId);
    boolean accessProduct(Authentication authentication, int productId);
    boolean accessAdmin(Authentication authentication);
}
