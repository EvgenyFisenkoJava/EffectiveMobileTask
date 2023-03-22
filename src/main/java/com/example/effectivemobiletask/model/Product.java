package com.example.effectivemobiletask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private float price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "keywords")
    private String keywords;
    @OneToMany(mappedBy = "product")
    private List<ProductFeature> features;
    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;
    @ManyToOne
    private Discount discount;
    @ManyToOne
    private Company company;
    @OneToMany(mappedBy = "product")
    private List<Feedback> feedbacks;
}
