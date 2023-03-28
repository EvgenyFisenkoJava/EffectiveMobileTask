package com.example.effectivemobiletask.dto;

import com.example.effectivemobiletask.model.Product;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.List;

@Data
public class DiscountDto {
    private int id;

    private short value;

    private int discountPeriod;
}
