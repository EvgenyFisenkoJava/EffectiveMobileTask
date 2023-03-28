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

    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "keywords")
    private String keywords;
    @Column(name = "active")
    private boolean active;
    @Column(name = "average_rating")
    private String averageRating;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductFeature> description;
    @ManyToOne
    private Discount discount;
    @ManyToOne
    private Company company;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks;

}
