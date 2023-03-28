package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.CompanyDto;
import com.example.effectivemobiletask.dto.mapper.CompanyMapper;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.UserProfile;
import com.example.effectivemobiletask.repository.CompanyRepository;
import com.example.effectivemobiletask.repository.UserRepository;
import com.example.effectivemobiletask.service.CompanyService;
import com.example.effectivemobiletask.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
/**

 This class implements the CompanyService interface and provides functionalities
 related to companies.
*/
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    /**

     * Adds a new company to the system with the given details provided in the CompanyDto parameter.
     * @param authentication The authentication object representing the currently logged in user.
     * @param companyDto The CompanyDto object containing the details of the company to be added.
     * @return The CompanyDto object representing the added company.
     */
    @Override
    public CompanyDto addCompany(Authentication authentication, CompanyDto companyDto) {
        Company company = new Company();
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        company.setUserProfile(userProfile);
        company.setActive(false);
        company.setName(companyDto.getName());
        company.setDescription(companyDto.getDescription());

        return companyMapper.companyToCompanyDto(companyRepository.save(company));
    }

    /**

     * Retrieves the company details by its ID.
     * @param companyId The ID of the company to retrieve.
     * @return The CompanyDto object representing the retrieved company.
     */
    @Override
    public CompanyDto getCompany(int companyId) {
        return companyMapper.companyToCompanyDto(Objects.requireNonNull(companyRepository.findById(companyId)).orElse(null));
    }

    /**

     * Deletes a company from the system with the given ID, provided the user has necessary authorization.
     * @param authentication The authentication object representing the currently logged in user.
     * @param companyId The ID of the company to remove.
     */
    @Override
    public void removeCompany(Authentication authentication, int companyId) {
        if (securityService.accessUser(authentication, companyId) || securityService.accessAdmin(authentication)) {
            companyRepository.deleteById(companyId);
        }
    }

    /**

     * Changes the status of a company to either active or inactive based on the current status and user authorization.
     * @param companyId The ID of the company whose status is to be changed.
     * @param authentication The authentication object representing the currently logged in user.
     * @throws NotAuthorizedException if the user is not authorized to perform the operation.
     */
    @Override
    public void setStatus(int companyId, Authentication authentication) throws NotAuthorizedException {
        Company company = companyRepository.findById(companyId).orElse(null);
        assert company != null;
        boolean status = company.isActive();
        if (securityService.accessAdmin(authentication)) {
            if (status) {
                company.setActive(false);
            } else {
                company.setActive(true);
            }
        } else {
            throw new NotAuthorizedException("not authorized");
        }
        companyRepository.save(company);
    }
}
