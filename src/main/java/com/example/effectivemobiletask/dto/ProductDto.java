package com.example.effectivemobiletask.dto;

import com.example.effectivemobiletask.model.ProductFeature;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.util.List;

@Data
public class ProductDto {
    private int id;

    private String title;
    private List<ProductFeatureDto> description;
    private float price;
    private int quantity;
    private String keywords;

}
