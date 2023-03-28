package com.example.effectivemobiletask.dto.mapper;

import com.example.effectivemobiletask.dto.CompanyDto;
import com.example.effectivemobiletask.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CompanyMapper {

    @Mapping(source = "image.filePath", target = "image")
    CompanyDto companyToCompanyDto(Company company);

    @Mapping(target = "image", ignore = true)
    Company companyDtoToCompany(CompanyDto companyDto);

}
