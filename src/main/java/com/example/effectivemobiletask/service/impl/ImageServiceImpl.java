package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.CompanyDto;
import com.example.effectivemobiletask.dto.mapper.CompanyMapper;
import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.Image;
import com.example.effectivemobiletask.repository.CompanyRepository;
import com.example.effectivemobiletask.repository.ImageRepository;
import com.example.effectivemobiletask.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public void uploadImage(int companyId, MultipartFile imageFile, Authentication authentication) throws IOException {
        Company company = companyRepository.findById(companyId).orElse(null);
        Image image = imageRepository.findByCompanyId(companyId);

        if (image == null) {
            image = new Image();
        }
        assert company != null;
        image.setFilePath("/company/" + company.getId() + "/image");
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setData(imageFile.getBytes());
        image.setCompany(company);
        imageRepository.save(image);
        CompanyDto companyDto = companyMapper.companyToCompanyDto(company);
companyDto.setImage(image.getFilePath());
    }
    @Override
    public byte[] getImage(int companyId) {

        Image image = imageRepository.findByCompanyId(companyId);
        return image.getData();
    }


}
