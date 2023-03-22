package com.example.effectivemobiletask.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
@GetMapping("/me")
public ResponseEntity<?> getUser(){
    return ResponseEntity.ok().build();
}

@GetMapping("/me/balance")
    public int getBalance(){
    return 0;
}

@GetMapping("/me/notification")
    public List<?> getNotifications(){
    return null;
}





}
