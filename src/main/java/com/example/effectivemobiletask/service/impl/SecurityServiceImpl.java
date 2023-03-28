package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.repository.CompanyRepository;
import com.example.effectivemobiletask.repository.ProductRepository;
import com.example.effectivemobiletask.repository.UserRepository;
import com.example.effectivemobiletask.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**

 * Implementation of the {@link SecurityService} interface that provides methods
 * for checking user access permissions.
 */
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**

     * Checks whether the user has access to the specified company.
     * @param authentication the user authentication information
     * @param companyId the ID of the company to be accessed
     * @return true if the user has access, false otherwise
     * @throws NoSuchElementException if the specified company is not found
     */
    @Override
    public boolean accessUser(Authentication authentication, int companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow();
        int userCompanyId = company.getUserProfile().getId();
        int useProfileId = userRepository.findByUsername(authentication.getName()).getId();
        return useProfileId == userCompanyId;

    }

    /**

     * Checks whether the user has access to the specified product.
     * @param authentication the user authentication information
     * @param productId the ID of the product to be accessed
     * @return true if the user has access, false otherwise
     * @throws NoSuchElementException if the specified product is not found
     */

    @Override
    public boolean accessProduct(Authentication authentication, int productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        int userProductId = product.getCompany().getUserProfile().getId();
        int useProfileId = userRepository.findByUsername(authentication.getName()).getId();
        return useProfileId == userProductId;
    }

    /**

     * Checks whether the user has administrative access.
     * @param authentication the user authentication information
     * @return true if the user has administrative access, false otherwise
     */
    @Override
    public boolean accessAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

    }
}
