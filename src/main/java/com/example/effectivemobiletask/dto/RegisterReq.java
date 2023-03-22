package com.example.effectivemobiletask.dto;

import com.example.effectivemobiletask.model.Role;
import lombok.Data;

@Data
public class RegisterReq {
    private String username;
    private String password;
    private String email;
    private Role role;

}
