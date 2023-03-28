package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.NotificationDto;
import com.example.effectivemobiletask.dto.UserDto;
import com.example.effectivemobiletask.dto.mapper.NotificationMapper;
import com.example.effectivemobiletask.dto.mapper.UserMapper;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import com.example.effectivemobiletask.model.Notification;
import com.example.effectivemobiletask.model.UserProfile;
import com.example.effectivemobiletask.repository.NotificationRepository;
import com.example.effectivemobiletask.repository.UserRepository;
import com.example.effectivemobiletask.service.SecurityService;
import com.example.effectivemobiletask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
/**

 * Implementation of UserService interface providing functionality for managing user profiles.
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final UserMapper userMapper;
    private final SecurityService securityService;

    /**
     * Returns the user information associated with the given userId.
     *
     * @param userId the ID of the user to retrieve
     * @param authentication the authentication object containing the user's credentials
     * @return a UserDto object representing the user's information
     */
    @Override
    public UserDto getUser(int userId, Authentication authentication) {
        UserProfile userProfile = userRepository.findById(userId).orElse(null);

        return userMapper.userToUserDto(userProfile);
    }


    /**
     * Sets the status of a user to either active or inactive (banned).
     *
     * @param userId the ID of the user whose status is being updated
     * @param authentication the authentication object containing the user's credentials
     * @return a message indicating whether the user was banned or unbanned
     * @throws NotAuthorizedException if the user does not have admin privileges
     */
    @Override
    public String setUserStatus(int userId, Authentication authentication) throws NotAuthorizedException {
        UserProfile userProfile = userRepository.findById(userId).orElse(null);
        assert userProfile != null;
        boolean status = userProfile.isActive();
        String message;
        if (securityService.accessAdmin(authentication)) {
            if (status) {
                userProfile.setActive(false);
                message = "user" + userProfile.getUsername() + "has been banned";
            } else {
                userProfile.setActive(true);
                message = "user" + userProfile.getUsername() + "has been unbanned";
            }
        } else {
            throw new NotAuthorizedException("not authorized");
        }
        userRepository.save(userProfile);
        return message;

    }

    /**
     * Removes the user with the specified ID.
     *
     * @param userId the ID of the user to remove
     * @param authentication the authentication object containing the user's credentials
     */
    @Override
    public void removeUser(int userId, Authentication authentication) {
        if (securityService.accessAdmin(authentication)) {
            userRepository.deleteById(userId);
        }
    }

    /**
     * Adds a new notification for a user.
     *
     * @param userId the ID of the user to add the notification for
     * @param notificationDto the NotificationDto object containing the notification information
     * @param authentication the authentication object containing the user's credentials
     * @return a NotificationDto object representing the newly created notification
     * @throws NotAuthorizedException if the user does not have admin privileges
     */
    @Override
    public NotificationDto addNotification(int userId, NotificationDto notificationDto, Authentication authentication) throws NotAuthorizedException {
        Notification notification = new Notification();
        if (securityService.accessAdmin(authentication)) {
            notification.setUserProfile(userRepository.findById(userId).orElse(null));
            notification.setText(notificationDto.getText());
            notification.setDate(LocalDate.now());
        } else {
            throw new NotAuthorizedException("not authorized");
        }
        return notificationMapper.notifToNotifDto(notificationRepository.save(notification));
    }

    /**
     * Retrieves a list of notifications for the authenticated user.
     *
     * @param authentication the authentication object containing the user's credentials
     * @return a list of NotificationDto objects representing the user's notifications
     */
    @Override
    public List<NotificationDto> getNotifications(Authentication authentication) {
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        List<Notification> notificationList = notificationRepository.findAllByUserProfileId(userProfile.getId());
        return notificationMapper.listToDtoList(notificationList);
    }

    /**
     * Adds a specified amount to the authenticated user's account balance.
     *
     * @param balance the amount of balance to add
     * @param authentication the authentication object containing the user's credentials
     */
    @Override
    public void addBalance(double balance, Authentication authentication) {
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        double oldBalance = userProfile.getBalance();
        double newBalance = oldBalance + balance;
        userProfile.setBalance(newBalance);
        userRepository.save(userProfile);
    }

    /**
     * Adds a specified amount to the account balance of the user with the specified ID.
     *
     * @param userId the ID of the user whose account balance is being updated
     * @param balance the amount of balance to add
     * @param authentication the authentication object containing the user's credentials
     * @throws NotAuthorizedException if the user does not have admin privileges
     */
    @Override
    public void addAnyBalance(int userId, double balance, Authentication authentication) throws NotAuthorizedException {
        UserProfile userProfile = userRepository.findById(userId).orElse(null);
        assert userProfile != null;
        if (securityService.accessAdmin(authentication)) {
            double oldBalance = userProfile.getBalance();
            double newBalance = oldBalance + balance;
            userProfile.setBalance(newBalance);
            userRepository.save(userProfile);
        } else {
            throw new NotAuthorizedException("not authorized");
        }
    }
}
