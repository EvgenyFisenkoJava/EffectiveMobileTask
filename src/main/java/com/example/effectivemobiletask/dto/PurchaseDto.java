package com.example.effectivemobiletask.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class PurchaseDto {
    private int id;
    private float price;
    private String product;

    private String productDescription;

    private String company;

    private LocalDateTime localDateTime;

    private String companyDescription;

    private int productId;

    private int quantity;
}
