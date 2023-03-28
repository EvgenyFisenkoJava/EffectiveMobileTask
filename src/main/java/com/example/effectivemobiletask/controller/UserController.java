package com.example.effectivemobiletask.controller;

import com.example.effectivemobiletask.dto.NotificationDto;
import com.example.effectivemobiletask.dto.UserDto;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import com.example.effectivemobiletask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/balance")
    public ResponseEntity addBalance(@RequestParam double balance, Authentication authentication) {
        userService.addBalance(balance, authentication);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/balance/{id}")
    public ResponseEntity addAnyBalance(@PathVariable("id") int userId,
                                        @RequestParam double balance, Authentication authentication) throws NotAuthorizedException {
        userService.addAnyBalance(userId, balance, authentication);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/message/{id}")
    public ResponseEntity<NotificationDto> addNotification(@PathVariable("id") int userId,
                                                           @RequestBody NotificationDto notificationDto,
                                                           Authentication authentication) throws NotAuthorizedException {
        userService.addNotification(userId, notificationDto, authentication);
        return ResponseEntity.ok(notificationDto);
    }

    @GetMapping("/notification")
    public ResponseEntity<List<NotificationDto>> getNotifications(Authentication authentication) {

        List<NotificationDto> notificationList = userService.getNotifications(authentication);
        return ResponseEntity.ok(notificationList);
    }

    @PatchMapping(value = ("/{id}/status"))
    public ResponseEntity<String> setUserStatus(@PathVariable("id") int userId,
                                                Authentication authentication) throws NotAuthorizedException {
        userService.setUserStatus(userId, authentication);
        return ResponseEntity.ok("user status has been changed");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") int userId,
                                              Authentication authentication) {
        userService.removeUser(userId, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
