package com.example.effectivemobiletask.repository;

import com.example.effectivemobiletask.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<Purchase, Integer> {

}
