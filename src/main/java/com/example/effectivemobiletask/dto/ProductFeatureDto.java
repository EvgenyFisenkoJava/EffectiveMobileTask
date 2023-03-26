package com.example.effectivemobiletask.dto;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;

@Data

public class ProductFeatureDto {
    private int id;
    private String title;
    private String description;
}
