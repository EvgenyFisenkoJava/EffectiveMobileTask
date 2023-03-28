package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
@GetMapping("/me")
public ResponseEntity<?> getUser(){
    return ResponseEntity.ok().build();
}

@PatchMapping("/me/balance")
    public ResponseEntity addBalance(@RequestParam double balance, Authentication authentication){
    userService.addBalance(balance, authentication);
    return ResponseEntity.ok().build();
}
    @PatchMapping("/admin/balance/{userId}")
    public ResponseEntity addAnyBalance(@PathVariable int userId,
                                        @RequestParam double balance, Authentication authentication) {
        userService.addAnyBalance(userId, balance, authentication);
        return ResponseEntity.ok().build();
    }

@GetMapping("/me/notification")
    public List<?> getNotifications(){
    return null;
}

}
