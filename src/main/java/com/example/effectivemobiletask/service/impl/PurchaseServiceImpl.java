package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.PurchaseDto;
import com.example.effectivemobiletask.dto.mapper.ProductMapper;
import com.example.effectivemobiletask.dto.mapper.PurchaseMapper;
import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.model.Purchase;
import com.example.effectivemobiletask.model.UserProfile;
import com.example.effectivemobiletask.repository.CompanyRepository;
import com.example.effectivemobiletask.repository.HistoryRepository;
import com.example.effectivemobiletask.repository.ProductRepository;
import com.example.effectivemobiletask.repository.UserRepository;
import com.example.effectivemobiletask.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final PurchaseMapper purchaseMapper;
    @Override
    public void buyProduct(int productId, int wantedQuantity, Authentication authentication) {

        Product product = Objects.requireNonNull(productRepository.findById(productId).orElse(null));
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        int quantity = product.getQuantity();
        double balance = userProfile.getBalance();
        double price = product.getPrice();

        if (quantity >= wantedQuantity && balance >= price) {
            quantity = quantity - wantedQuantity;
            product.setQuantity(quantity);
            Purchase purchase = new Purchase();
            purchase.setCompany(product.getCompany().getName());
            purchase.setProduct(product.getTitle());
           // purchase.setProductDescription(product.getDescription());
            purchase.setPrice(product.getPrice());
            purchase.setCompanyDescription(product.getCompany().getDescription());
            purchase.setUserProfile(userRepository.findByUsername(authentication.getName()));
            purchase.setLocalDateTime(LocalDateTime.now());
            purchase.setProductId(productId);
            purchase.setQuantity(wantedQuantity);
            historyRepository.save(purchase);
            productRepository.save(product);
        }
        if (quantity < wantedQuantity) {
            throw new RuntimeException("No such quantity");
        }
        if (balance < price) {
            throw new RuntimeException("balance is not enough");
        }
    }

    @Override
    public void returnProduct(int productId, Authentication authentication) {

        Product product = Objects.requireNonNull(productRepository.findById(productId).orElse(null));
        Purchase purchase = historyRepository.findPurchaseByProductId(productId);
        if(purchase.getLocalDateTime().isBefore(LocalDateTime.now().minusDays(1))) {
            throw new RuntimeException("return is possible only 24 hours since the purchase date");

        }
        else {
            product.setQuantity(product.getQuantity() + purchase.getQuantity());
            historyRepository.deleteById(purchase.getId());
            productRepository.save(product);
        }
    }

    @Override
    public Collection<PurchaseDto> getHistory(Authentication authentication) {
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        return purchaseMapper.purchaseToPurchaseDto(historyRepository.findAllByUserProfileId(userProfile.getId()));
    }

    @Override
    public Collection<PurchaseDto> getAnyHistory(int userId, Authentication authentication) {
        return purchaseMapper.purchaseToPurchaseDto(historyRepository.findAllByUserProfileId(userId));
    }
}
