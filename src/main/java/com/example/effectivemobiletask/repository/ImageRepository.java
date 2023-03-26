package com.example.effectivemobiletask.repository;

import com.example.effectivemobiletask.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findByCompanyId(int companyId);
}
