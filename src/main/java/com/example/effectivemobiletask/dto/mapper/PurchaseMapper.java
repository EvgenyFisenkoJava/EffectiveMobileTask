package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.PurchaseDto;
import com.example.effectivemobiletask.model.Purchase;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface PurchaseMapper {
    Collection<PurchaseDto> purchaseToPurchaseDto(Collection<Purchase> purchase);

}
