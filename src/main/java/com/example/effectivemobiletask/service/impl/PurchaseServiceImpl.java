package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.FeedbackDto;
import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.PurchaseDto;
import com.example.effectivemobiletask.dto.mapper.FeedbackMapper;
import com.example.effectivemobiletask.dto.mapper.ProductMapper;
import com.example.effectivemobiletask.dto.mapper.PurchaseMapper;
import com.example.effectivemobiletask.model.Feedback;
import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.model.Purchase;
import com.example.effectivemobiletask.model.UserProfile;
import com.example.effectivemobiletask.repository.*;
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
    private final FeedbackMapper feedbackMapper;
    private final FeedBackRepository feedBackRepository;


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
        if (purchase.getLocalDateTime().isBefore(LocalDateTime.now().minusDays(1))) {
            throw new RuntimeException("return is possible only 24 hours since the purchase date");

        } else {
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

    @Override
    public FeedbackDto addFeedback(int productId, FeedbackDto feedbackDto, Authentication authentication) {
        Feedback feedback = new Feedback();
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        Product product = productRepository.findById(productId).orElse(null);
if(feedBackRepository.findByProductIdAndAndUserProfileId(productId, userProfile.getId())==null) {
        feedback.setProduct(product);
        feedback.setUserProfile(userProfile);
        feedback.setText(feedbackDto.getText());
        feedback.setRating(feedbackDto.getRating());
        feedBackRepository.save(feedback);
    } else {
        throw new RuntimeException("you have already left feedback for this product");

}
        List<Feedback> feedbackList = feedBackRepository.findAllByProductId(productId);
        Float average = (float) feedbackList.stream().mapToInt(Feedback::getRating).average().orElse(0.0);
        assert product != null;
        product.setAverageRating(String.format("%.1f",average));
        return feedbackMapper.feedbackToFeedbackDto(feedBackRepository.save(feedback));
    }

    @Override
    public List<FeedbackDto> getFeedbacks(int productId) {
        List<Feedback> feedbackList = feedBackRepository.findAllByProductId(productId);

        return feedbackMapper.feedBackListToFeedbackDtoList(feedbackList);
    }
}
