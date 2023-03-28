package com.example.effectivemobiletask.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UserDto {
    private int id;
    private String username;
    private String email;
    private double balance;
    private boolean active;
}
