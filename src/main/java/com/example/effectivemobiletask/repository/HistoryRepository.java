package com.example.effectivemobiletask.repository;

import com.example.effectivemobiletask.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findAllByUserProfileId(int id);

    boolean existsByProductIdAndUserProfileId(int productId, int userId);
}
