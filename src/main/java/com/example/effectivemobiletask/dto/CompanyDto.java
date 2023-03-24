package com.example.effectivemobiletask.dto;

import com.example.effectivemobiletask.model.Image;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
public class CompanyDto {
    private int id;
    private String name;
    private  String description;
    private String image;

}
