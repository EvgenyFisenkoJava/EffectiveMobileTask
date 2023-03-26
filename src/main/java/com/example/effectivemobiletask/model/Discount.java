package com.example.effectivemobiletask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "value")
    private short value;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "discount_period")
    private int discountPeriod;
    @OneToMany(mappedBy = "discount")
    private List<Product> products;
};


