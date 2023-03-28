package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.dto.NotificationDto;
import com.example.effectivemobiletask.dto.UserDto;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserDto getUser(int userId, Authentication authentication);
    String setUserStatus(int userId, Authentication authentication) throws NotAuthorizedException;
    void removeUser(int userId, Authentication authentication);
NotificationDto addNotification(int userId, NotificationDto notificationDto, Authentication authentication) throws NotAuthorizedException;
List<NotificationDto> getNotifications (Authentication authentication);
    void addBalance(double balance, Authentication authentication);
    void addAnyBalance(int userId, double balance, Authentication authentication) throws NotAuthorizedException;
}
