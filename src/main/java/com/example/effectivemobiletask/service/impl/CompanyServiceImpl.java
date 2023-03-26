package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.CompanyDto;
import com.example.effectivemobiletask.dto.mapper.CompanyMapper;
import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.UserProfile;
import com.example.effectivemobiletask.repository.CompanyRepository;
import com.example.effectivemobiletask.repository.ImageRepository;
import com.example.effectivemobiletask.repository.ProductRepository;
import com.example.effectivemobiletask.repository.UserRepository;
import com.example.effectivemobiletask.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;

    @Override
    public CompanyDto addCompany(Authentication authentication, CompanyDto companyDto) throws IOException {
        Company company = companyMapper.companyDtoToCompany(companyDto);
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        company.setUserProfile(userProfile);
        company.setActive(false);

        return companyMapper.companyToCompanyDto(companyRepository.save(company));
    }

    @Override
    public CompanyDto getCompany(int companyId) {
        return companyMapper.companyToCompanyDto(Objects.requireNonNull(companyRepository.findById(companyId)).orElse(null));
    }

    @Override
    public void removeCompany(Authentication authentication, int companyId) {
        // imageRepository.delete(imageRepository.findByCompanyId(companyId));
        //productRepository.delete(productRepository.deleteByCompanyId(companyId));
        companyRepository.deleteById(companyId);
    }

    @Override
    public String setStatus(Authentication authentication, int companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        assert company != null;
        boolean status = company.isActive();
        String message;
        if (status) {
            company.setActive(false);
            message = "company" + company + "has been blocked";
        } else {
            company.setActive(true);
            message = "company" + company + "has been approved";
        }
        companyRepository.save(company);
        return message;
    }
}
