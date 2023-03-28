package com.example.effectivemobiletask.dto;

import lombok.Data;

import javax.persistence.Column;
@Data
public class FeedbackDto {
    private int id;

    private String text;

    private int rating;
}
