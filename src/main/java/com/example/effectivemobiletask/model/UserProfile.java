package com.example.effectivemobiletask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "balance")
    private double balance;
    @Column(name = "status")
    private boolean active;
    @OneToMany(mappedBy = "userProfile",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Company> companies;
    @OneToMany(mappedBy = "userProfile",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Notification> notifications;
    @OneToMany(mappedBy = "userProfile",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Purchase> purchase;


}
