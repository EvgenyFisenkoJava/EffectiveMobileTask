package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.mapper.DiscountMapper;
import com.example.effectivemobiletask.dto.mapper.ProductMapper;
import com.example.effectivemobiletask.model.Company;
import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.model.Purchase;
import com.example.effectivemobiletask.repository.*;
import com.example.effectivemobiletask.service.DiscountService;
import com.example.effectivemobiletask.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;




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
        return productMapper.productToProductDto(productRepository
                .findById(productId).orElse(null));
    }

    @Override
    public ProductDto buyProduct(int productId, int wantedQuantity, Authentication authentication) {
        ProductDto productDto = productMapper.productToProductDto(productRepository
                .findById(productId).orElse(null));
Product product = Objects.requireNonNull(productRepository.findById(productId).orElse(null));

int quantity = product.getQuantity();
        if(quantity >= wantedQuantity) {
            quantity = quantity - wantedQuantity;
            product.setQuantity(quantity);
            Purchase purchase = new Purchase();
            purchase.setCompany(product.getCompany().getName());
            purchase.setProduct(product.getTitle());
            purchase.setProductDescription(product.getDescription());
            purchase.setPrice(product.getPrice());
            purchase.setCompanyDescription(product.getCompany().getDescription());
            purchase.setUserProfile(userRepository.findByUsername(authentication.getName()));
            purchase.setLocalDateTime(LocalDateTime.now());
            historyRepository.save(purchase);
            productRepository.save(product);
        }
        else throw new RuntimeException("No such quantity");
        return productDto;
    }

}
