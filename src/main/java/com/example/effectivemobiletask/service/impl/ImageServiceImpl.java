package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.Image;
import com.example.effectivemobiletask.repository.CompanyRepository;
import com.example.effectivemobiletask.repository.ImageRepository;
import com.example.effectivemobiletask.service.ImageService;
import com.example.effectivemobiletask.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
/**
 * This class implements the ImageService interface, providing methods for uploading and retrieving images associated with a company.
 */

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CompanyRepository companyRepository;
    private final SecurityService securityService;

    /**
     * Uploads an image for the specified company.
     *
     * @param companyId the ID of the company to associate the image with
     * @param imageFile the MultipartFile object containing the image data to be uploaded
     * @param authentication an object representing the authentication details of the user making the request
     * @throws IOException if an I/O error occurs while reading the image data
     * @throws NotAuthorizedException if the user making the request is not authorized to upload images for the specified company
     */
    @Override
    public void uploadImage(int companyId, MultipartFile imageFile, Authentication authentication) throws IOException, NotAuthorizedException {
        Company company = companyRepository.findById(companyId).orElse(null);
        Image image = imageRepository.findByCompanyId(companyId);

        if (image == null) {
            image = new Image();
        }
        assert company != null;
        if (securityService.accessUser(authentication, companyId)
                || securityService.accessAdmin(authentication)) {
            image.setFilePath("/company/" + company.getId() + "/image");
            image.setFileSize(imageFile.getSize());
            image.setMediaType(imageFile.getContentType());
            image.setData(imageFile.getBytes());
            image.setCompany(company);
            imageRepository.save(image);
            company.setImage(image);
            companyRepository.save(company);
        } else {
            throw new NotAuthorizedException("not authorized");
        }
    }

    /**
     * Retrieves the image data for the specified company.
     *
     * @param companyId the ID of the company to retrieve the image for
     * @return a byte array containing the image data, or null if no image is associated with the specified company
     */
    @Override
    public byte[] getImage(int companyId) {
        Image image = imageRepository.findByCompanyId(companyId);
        return image.getData();
    }
}
