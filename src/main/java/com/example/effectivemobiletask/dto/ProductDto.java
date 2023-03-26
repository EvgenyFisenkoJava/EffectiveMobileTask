package com.example.effectivemobiletask.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ProductDto {
    private int id;

    private String title;

    private String description;

    private float price;

    private int quantity;

    private String keywords;

}
