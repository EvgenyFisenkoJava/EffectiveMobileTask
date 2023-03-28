package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import com.example.effectivemobiletask.dto.CompanyDto;
import org.springframework.security.core.Authentication;

public interface CompanyService {
    CompanyDto addCompany(Authentication authentication, CompanyDto companyDto);

    CompanyDto getCompany(int companyId);

    void removeCompany(Authentication authentication, int companyId);

    void setStatus(int companyId, Authentication authentication) throws NotAuthorizedException;


}
