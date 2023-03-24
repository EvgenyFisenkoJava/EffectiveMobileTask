package com.example.effectivemobiletask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private  String description;
    @Column(name = "active")
    private boolean active;
    @OneToMany(mappedBy = "company")
    private List<Product> product;
    @ManyToOne
    private UserProfile userProfile;

    @OneToOne
    private Image image;
}
