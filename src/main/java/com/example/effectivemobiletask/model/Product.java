package com.example.effectivemobiletask.model;

import lombok.*;

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
    @OneToMany(mappedBy = "product")
    private List<ProductFeature> description;
    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;
    @ManyToOne
    private Discount discount;
    @ManyToOne
    private Company company;
    @OneToMany(mappedBy = "product")
    private List<Feedback> feedbacks;

}
