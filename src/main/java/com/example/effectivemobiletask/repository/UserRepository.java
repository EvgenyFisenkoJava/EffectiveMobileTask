package com.example.effectivemobiletask.repository;

import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserProfile, Integer> {
UserProfile findByUsername(String username);
}
