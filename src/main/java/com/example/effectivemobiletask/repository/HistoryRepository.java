package com.example.effectivemobiletask.repository;

import com.example.effectivemobiletask.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface HistoryRepository extends JpaRepository<Purchase, Integer> {
Purchase findPurchaseByProductId(int id);
Collection<Purchase> findAllByUserProfileId( int id);
}
