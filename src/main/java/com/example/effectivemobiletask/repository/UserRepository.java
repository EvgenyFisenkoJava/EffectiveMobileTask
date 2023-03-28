package com.example.effectivemobiletask.repository;

import com.example.effectivemobiletask.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findByUsername(String username);
}
