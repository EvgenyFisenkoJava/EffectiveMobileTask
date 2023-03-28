package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.model.Discount;
import com.example.effectivemobiletask.repository.DiscountRepository;
import com.example.effectivemobiletask.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
/**

 Implementation of {@link SchedulerService} that retrieves discounts from the database.
 */
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements SchedulerService {
    private final DiscountRepository discountRepository;

    /**

     * Returns a collection of all discounts.
     * @return a collection of {@code Discount} objects representing all discounts in the database.
     */
    @Override
    public Collection<Discount> getDiscounts() {
        return discountRepository.findAll();
    }
}
