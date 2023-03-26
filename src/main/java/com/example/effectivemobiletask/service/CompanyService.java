package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.dto.CompanyDto;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface CompanyService {
    CompanyDto addCompany(Authentication authentication, CompanyDto companyDto) throws IOException;

    CompanyDto getCompany(int companyId);

    void removeCompany(Authentication authentication, int companyId);

    String setStatus(Authentication authentication, int companyId);


}
