package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.CreateProductDto;
import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.ProductFeatureDto;
import com.example.effectivemobiletask.dto.mapper.CreateProductDtoMapper;
import com.example.effectivemobiletask.dto.mapper.ProductFeatureMapper;
import com.example.effectivemobiletask.dto.mapper.ProductMapper;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.model.ProductFeature;
import com.example.effectivemobiletask.repository.CompanyRepository;
import com.example.effectivemobiletask.repository.ProductFeatureRepository;
import com.example.effectivemobiletask.repository.ProductRepository;
import com.example.effectivemobiletask.service.ProductService;
import com.example.effectivemobiletask.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
/**

 * This class implements ProductService interface providing methods to manage
 * products and their features.
 */

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductFeatureRepository productFeatureRepository;
    private final ProductFeatureMapper productFeatureMapper;
    private final CreateProductDtoMapper createProductDtoMapper;
    private final SecurityService securityService;

    /**

     * Adds a new product to a company's product list.
     * @param createProductDto - DTO representing the product to be added.
     * @param authentication - authentication object containing information about the user making the request.
     * @param companyId - the ID of the company for which the product is being added.
     * @return DTO representing the added product.
     * @throws NotAuthorizedException if the user is not authorized to perform the operation.
     */
    @Override
    public CreateProductDto addProduct(CreateProductDto createProductDto, Authentication authentication, int companyId) throws NotAuthorizedException {
        Product product = new Product();

        Company company = companyRepository.findById(companyId).orElse(null);
        if (securityService.accessUser(authentication, companyId)
                || securityService.accessAdmin(authentication)
                && Objects.requireNonNull(company).isActive()) {
            product.setCompany(company);
            product.setActive(false);
            product.setKeywords(createProductDto.getKeywords());
            product.setPrice(createProductDto.getPrice());
            product.setQuantity(createProductDto.getQuantity());
        } else {
            throw new NotAuthorizedException("not authorized");
        }
        return createProductDtoMapper.productToProductDto(productRepository.save(product));
    }

    /**

     * Removes a product from the system.
     * @param productId - the ID of the product to be removed.
     * @param authentication - authentication object containing information about the user making the request.
     * @throws NotAuthorizedException if the user is not authorized to perform the operation.
     */
    @Override
    public void removeProduct(int productId, Authentication authentication) throws NotAuthorizedException {
        if (securityService.accessProduct(authentication, productId)
                || securityService.accessAdmin(authentication)) {
            productRepository.deleteById(productId);
        } else {
            throw new NotAuthorizedException("not authorized");
        }
    }

    /**

     * Sets the status of a product to either active or inactive.
     * @param productId - the ID of the product to be updated.
     * @param authentication - authentication object containing information about the user making the request.
     * @throws NotAuthorizedException if the user is not authorized to perform the operation.
     */
    @Override
    public void setStatus(int productId, Authentication authentication) throws NotAuthorizedException {
        Product product = productRepository.findById(productId).orElse(null);
        assert product != null;
        boolean status = product.isActive();
        if (securityService.accessAdmin(authentication)) {
            if (status) {
                product.setActive(false);
            } else {
                product.setActive(true);
            }
        } else {
            throw new NotAuthorizedException("not authorized");
        }
        productRepository.save(product);
    }

    /**

     * Retrieves a product's details.
     * @param productId - the ID of the product to be retrieved.
     * @return DTO representing the retrieved product.
     */
    @Override
    public ProductDto getProduct(int productId) {
        Product product = productRepository.findById(productId).orElse(null);
        assert product != null;
        if (product.isActive()) {
             return productMapper.productToProductDto(
                    productRepository.findById(productId).orElse(null));
        }
        return new ProductDto();
    }

    /**
     * Updates an existing product with the specified product ID using the information from the provided
     * {@link CreateProductDto} object. This method can only be called by an administrator.
     *
     * @param productId the ID of the product to be updated
     * @param createProductDto the {@link CreateProductDto} object containing the updated information
     * @param authentication the {@link Authentication} object representing the authenticated user
     * @return the updated {@link CreateProductDto} object for the product
     * @throws NotAuthorizedException  if the user is not authorized to perform the operation
     */
    @Override
    public CreateProductDto updateProduct(int productId, CreateProductDto createProductDto, Authentication authentication) throws NotAuthorizedException {
        Product product = productRepository.findById(productId).orElse(null);
        assert product != null;
        if (securityService.accessAdmin(authentication)) {
            product.setTitle(createProductDto.getTitle());
            product.setQuantity(createProductDto.getQuantity());
            product.setPrice(createProductDto.getPrice());
            product.setKeywords(createProductDto.getKeywords());
        } else {
            throw new NotAuthorizedException("not authorized");
        }
        return createProductDtoMapper.productToProductDto(productRepository.save(product));
    }

    /**
     * Adds a feature to the product with the specified product ID using the information from the provided
     * {@link ProductFeatureDto} object. This method can only be called by an administrator or by a user who
     * has access to the specified product.
     *
     * @param productId the ID of the product to which the feature will be added
     * @param productFeatureDto the {@link ProductFeatureDto} object containing the feature information
     * @param authentication the {@link Authentication} object representing the authenticated user
     * @return the {@link ProductFeatureDto} object for the added feature
     * @throws NotAuthorizedException  if the user is not authorized to perform the operation
     */
    @Override
    public ProductFeatureDto addFeature(int productId, ProductFeatureDto productFeatureDto, Authentication authentication) throws NotAuthorizedException {
        ProductFeature productFeature = new ProductFeature();
        Product product = productRepository.findById(productId).orElse(null);
        if (securityService.accessProduct(authentication, productId)
                || securityService.accessAdmin(authentication)) {
            productFeature.setProduct(product);
            productFeature.setTitle(productFeatureDto.getTitle());
            productFeature.setDescription(productFeatureDto.getDescription());
            productFeatureRepository.save(productFeature);
        } else {
            throw new NotAuthorizedException("not authorized");
        }
        return productFeatureMapper.productFeatureToProductFeatureDto(productFeature);
    }
}
