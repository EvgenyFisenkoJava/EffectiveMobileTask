package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.DiscountDto;
import com.example.effectivemobiletask.model.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper
public interface DiscountMapper {
    DiscountDto discountToDiscountDto(Discount discount);

    @Mapping(target = "discount.time", ignore = true)
    Discount discountDtoToDiscount(DiscountDto discountDto);

    Collection<DiscountDto> discountsToDiscountDtos(Collection<Discount> discount);
}
