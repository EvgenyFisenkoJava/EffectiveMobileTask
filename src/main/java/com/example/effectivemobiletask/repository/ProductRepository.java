package com.example.effectivemobiletask.repository;

import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {


}
