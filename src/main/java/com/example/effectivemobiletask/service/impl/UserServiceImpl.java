package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.model.UserProfile;
import com.example.effectivemobiletask.repository.UserRepository;
import com.example.effectivemobiletask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void addBalance(double balance, Authentication authentication) {
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        double oldBalance = userProfile.getBalance();
        double newBalance = oldBalance + balance;
        userProfile.setBalance(newBalance);
        userRepository.save(userProfile);
    }

    @Override
    public void addAnyBalance(int userId, double balance, Authentication authentication) {
        UserProfile userProfile = userRepository.findById(userId).orElse(null);
        assert userProfile != null;
        double oldBalance = userProfile.getBalance();
        double newBalance = oldBalance + balance;
        userProfile.setBalance(newBalance);
        userRepository.save(userProfile);
    }
}
