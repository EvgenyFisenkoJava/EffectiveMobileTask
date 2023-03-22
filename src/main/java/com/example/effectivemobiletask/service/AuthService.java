package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.dto.RegisterReq;
import com.example.effectivemobiletask.model.Role;

public interface AuthService {
        boolean login(String username, String password);

        boolean register(RegisterReq registerReq, Role role);
    }


