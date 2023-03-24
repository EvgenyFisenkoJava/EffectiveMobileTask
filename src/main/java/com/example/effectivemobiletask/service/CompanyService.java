package com.example.effectivemobiletask.service;

import com.example.effectivemobiletask.dto.CompanyDto;
import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.Image;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompanyService {
    CompanyDto addCompany(Authentication authentication, CompanyDto companyDto) throws IOException;
    CompanyDto getCompany(int companyId);
    void removeCompany(Authentication authentication, int companyId);
    CompanyDto stopCompany(Authentication authentication, int companyId);


}
