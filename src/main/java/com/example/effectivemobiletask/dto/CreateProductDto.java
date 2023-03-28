package com.example.effectivemobiletask.dto;

import lombok.Data;

@Data
public class CreateProductDto {
    private int id;

    private String title;
    private float price;
    private int quantity;
    private String keywords;
}
