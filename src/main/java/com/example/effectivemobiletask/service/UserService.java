package com.example.effectivemobiletask.service;

import org.springframework.security.core.Authentication;

public interface UserService {

    void addBalance(double balance, Authentication authentication);
    void addAnyBalance(int userId, double balance, Authentication authentication);
}
