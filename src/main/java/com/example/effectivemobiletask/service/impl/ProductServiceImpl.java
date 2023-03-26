package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.ProductFeatureDto;
import com.example.effectivemobiletask.dto.mapper.ProductFeatureMapper;
import com.example.effectivemobiletask.dto.mapper.ProductMapper;
import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.model.ProductFeature;
import com.example.effectivemobiletask.repository.*;
import com.example.effectivemobiletask.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final ProductFeatureRepository productFeatureRepository;
    private final ProductFeatureMapper productFeatureMapper;


    @Override
    public ProductDto addProduct(ProductDto productDto, Authentication authentication, int companyId) {
        Product product = productMapper.productDtoToProduct(productDto);
        Company company = companyRepository.findById(companyId).orElse(null);
        product.setCompany(company);
        product.setActive(false);
        return productMapper.productToProductDto(productRepository.save(product));
    }

    @Override
    public void removeProduct(int productId, Authentication authentication) {
        productRepository.deleteById(productId);
    }

    @Override
    public void setStatus(int productId, Authentication authentication) {
        Product product = productRepository.findById(productId).orElse(null);
        assert product != null;
        boolean status = product.isActive();
        if (status) {
            product.setActive(false);
        } else {
            product.setActive(true);
        }
        productRepository.save(product);
    }

    @Override
    public ProductDto getProduct(int productId) {
        List<ProductFeature> productFeatureList = productFeatureRepository.findAllByProductId(productId);
        Product product = productRepository.findById(productId).orElse(null);

        return productMapper.productToProductDto(product);
    }

    @Override
    public ProductFeatureDto addFeature(int productId,ProductFeatureDto productFeatureDto, Authentication authentication) {
        ProductFeature productFeature = new ProductFeature();
        Product product = productRepository.findById(productId).orElse(null);
        productFeature.setProduct(product);
        productFeature.setTitle(productFeatureDto.getTitle());
        productFeatureRepository.save(productFeature);
        return productFeatureMapper.productFeatureToProductFeatureDto(productFeature);
    }

}
