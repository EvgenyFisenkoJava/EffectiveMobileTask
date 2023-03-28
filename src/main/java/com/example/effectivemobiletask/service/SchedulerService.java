package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.model.Discount;

import java.util.Collection;

public interface SchedulerService {
    Collection<Discount> getDiscounts();
}
