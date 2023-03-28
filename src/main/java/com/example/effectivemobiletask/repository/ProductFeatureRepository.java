package com.example.effectivemobiletask.repository;

import com.example.effectivemobiletask.model.ProductFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductFeatureRepository extends JpaRepository<ProductFeature, Integer> {
    List<ProductFeature> findAllByProductId(int id);
}
