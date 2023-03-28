package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.PurchaseDto;
import com.example.effectivemobiletask.model.Purchase;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PurchaseMapper {
    List<PurchaseDto> purchaseToPurchaseDto(Collection<Purchase> purchase);

}
