package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.CompanyDto;
import com.example.effectivemobiletask.dto.mapper.CompanyMapper;
import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.Image;
import com.example.effectivemobiletask.model.UserProfile;
import com.example.effectivemobiletask.repository.CompanyRepository;
import com.example.effectivemobiletask.repository.ImageRepository;
import com.example.effectivemobiletask.repository.UserRepository;
import com.example.effectivemobiletask.service.CompanyService;
import com.example.effectivemobiletask.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final ImageRepository imageRepository;
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
    }

    @Override
    public CompanyDto stopCompany(Authentication authentication, int companyId) {
        return null;
    }

}
