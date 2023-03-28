package com.example.effectivemobiletask.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationDto {
    private int id;
    private LocalDate date;
    private String text;
}
