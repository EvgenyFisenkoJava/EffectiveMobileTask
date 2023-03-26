package com.example.effectivemobiletask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_history")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "price")
    private double price;
    @Column(name = "product")
    private String product;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "company")
    private String company;
    @Column(name = "date")
    private LocalDateTime localDateTime;
    @Column(name = "company_description")
    private String companyDescription;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "quantity")
    private int quantity;
    @OneToOne
    private UserProfile userProfile;
}
