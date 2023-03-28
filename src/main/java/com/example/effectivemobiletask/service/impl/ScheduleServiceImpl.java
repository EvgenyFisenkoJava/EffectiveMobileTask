package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.mapper.DiscountMapper;
import com.example.effectivemobiletask.model.Discount;
import com.example.effectivemobiletask.repository.DiscountRepository;
import com.example.effectivemobiletask.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements SchedulerService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;

    @Override
    public Collection<Discount> getDiscounts() {
        return discountRepository.findAll();
    }
}
