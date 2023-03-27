package com.example.effectivemobiletask.repository;

import com.example.effectivemobiletask.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findByProductIdAndAndUserProfileId(int productId, int userId);
    Feedback findByProductId(int id);
    List<Feedback> findAllByProductId(int id);
}
