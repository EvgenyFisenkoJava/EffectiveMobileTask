package com.example.effectivemobiletask.repository;

import com.example.effectivemobiletask.model.Discount;
import com.example.effectivemobiletask.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductByDiscount(Discount discount);

    Collection<Product> findAllByKeywords(String keyWord);
}
