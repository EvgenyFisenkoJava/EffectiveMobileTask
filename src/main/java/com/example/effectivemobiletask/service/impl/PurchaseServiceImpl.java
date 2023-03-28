package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.FeedbackDto;
import com.example.effectivemobiletask.dto.PurchaseDto;
import com.example.effectivemobiletask.dto.mapper.FeedbackMapper;
import com.example.effectivemobiletask.dto.mapper.PurchaseMapper;
import com.example.effectivemobiletask.exceptions.*;
import com.example.effectivemobiletask.model.Feedback;
import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.model.Purchase;
import com.example.effectivemobiletask.model.UserProfile;
import com.example.effectivemobiletask.repository.FeedBackRepository;
import com.example.effectivemobiletask.repository.HistoryRepository;
import com.example.effectivemobiletask.repository.ProductRepository;
import com.example.effectivemobiletask.repository.UserRepository;
import com.example.effectivemobiletask.service.PurchaseService;
import com.example.effectivemobiletask.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final ProductRepository productRepository;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final PurchaseMapper purchaseMapper;
    private final FeedbackMapper feedbackMapper;
    private final FeedBackRepository feedBackRepository;
    private final SecurityService securityService;

    /**
     * Buys a specified quantity of a product with the given product ID, deducts the price from the user's balance,
     * updates the product's quantity, and saves the purchase and updated entities in the history and product repositories,
     * respectively. If the product is inactive or the company is blocked, or if the user has insufficient balance or
     * the desired quantity is not available, throws the corresponding exception.
     *
     * @param productId the ID of the product to purchase
     * @param wantedQuantity the desired quantity of the product to purchase
     * @param authentication the current authentication object for the user making the purchase
     * @throws NoSuchQuantityException if the desired quantity is not available
     * @throws BalanceIsnotEnoughException if the user has insufficient balance to complete the purchase
     * @throws BannedException if the product's company or the product itself is blocked
     */
    @Override
    public void buyProduct(int productId, int wantedQuantity, Authentication authentication) throws NoSuchQuantityException, BalanceIsnotEnoughException, BannedException {

        Product product = Objects.requireNonNull(productRepository.findById(productId).orElse(null));
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        UserProfile seller = product.getCompany().getUserProfile();
        int quantity = product.getQuantity();
        double balance = userProfile.getBalance();
        double price = product.getPrice();
        double commission = price / 100 * 5;
        double incomeBalance = price - (commission / 100 * price);
        boolean status = product.isActive() && product.getCompany().isActive();

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
            userProfile.setBalance(userProfile.getBalance() - price);
            userRepository.save(userProfile);
            seller.setBalance(seller.getBalance() + incomeBalance);
            userRepository.save(seller);
        }
        if (quantity < wantedQuantity || wantedQuantity <= 0) {
            throw new NoSuchQuantityException("No such quantity");
        }
        if (balance < price) {
            throw new BalanceIsnotEnoughException("balance is not enough");
        }
        if (!status) {
            throw new BannedException("this company or product is blocked");
        }
    }

    /**
     * Returns a purchased product with the given purchase ID, adds the product's price minus commission to the user's
     * balance, updates the product's quantity, and deletes the purchase entity from the history repository. If the
     * return period has expired or the user did not purchase the specified product, throws the corresponding exception.
     *
     * @param purchaseId the ID of the purchase to return
     * @param authentication the current authentication object for the user making the return
     * @throws ReturnPeriodExpiredException if the return period has expired
     */
    @Override
    public void returnProduct(int purchaseId, Authentication authentication) throws ReturnPeriodExpiredException {

        Purchase purchase = historyRepository.findById(purchaseId).orElse(null);
        Product product = Objects.requireNonNull(productRepository.findById(purchase.getProductId()).orElse(null));
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        UserProfile seller = product.getCompany().getUserProfile();
        double commission = product.getPrice() / 100 * 5;
        double price = purchase.getPrice() - commission;
        double incomeBalance = price * 100 / (100 - commission);
        if (purchase.getLocalDateTime().isBefore(LocalDateTime.now().minusDays(1))) {
            throw new ReturnPeriodExpiredException("return is possible only 24 hours since the purchase date");

        } else {
            product.setQuantity(product.getQuantity() + purchase.getQuantity());
            userProfile.setBalance(userProfile.getBalance() + incomeBalance);
            seller.setBalance(seller.getBalance() - price);
            userRepository.save(userProfile);
            userRepository.save(seller);
            historyRepository.deleteById(purchase.getId());
            productRepository.save(product);
        }
    }

    /**
     * Returns a list of purchase DTOs representing the purchase history for the current authenticated user.
     *
     * @param authentication the current authentication object for the user requesting their purchase history
     * @return a list of purchase DTOs representing the purchase history for the current authenticated user
     */
    @Override
    public List<PurchaseDto> getHistory(Authentication authentication) {
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        return purchaseMapper.purchaseToPurchaseDto(historyRepository.findAllByUserProfileId(userProfile.getId()));
    }

    /**
     * Returns a list of purchase DTOs representing the purchase history for the user with the given user ID. If the
     * current user does not have administrative access, throws a NotAuthorizedException.
     *
     * @param userId the ID of the user for whom to retrieve the purchase history
     * @param authentication the current authentication object for the user making the request
     * @return a list of purchase DTOs representing the purchase history for the specified user
     * @throws NotAuthorizedException if the current user does not have administrative access
     */
    @Override
    public List<PurchaseDto> getAnyHistory(int userId, Authentication authentication) throws NotAuthorizedException {
        List<PurchaseDto> list = new ArrayList<>();
        if (securityService.accessAdmin(authentication)) {
            list = purchaseMapper.purchaseToPurchaseDto(
                    historyRepository.findAllByUserProfileId(userId));
        } else {
            throw new NotAuthorizedException("not authorized");
        }
        return list;
    }

    /**
     * Adds a new feedback entity for the product with the given product ID, as long as the user has purchased the product
     * and has not already left feedback for it. Calculates and updates the average rating for the product and saves the
     * feedback entity in the feedback repository. If the user has not purchased the product or has already left feedback
     * for it, throws a RuntimeException.
     *
     * @param productId the ID of the product to which to add feedback
     * @param feedbackDto the feedback DTO containing the feedback text and rating to add
     * @param authentication the current authentication object for the user adding the feedback
     * @return the saved feedback DTO
     * @throws NotAuthorizedException if the current user is not authorized to add feedback
     */
    @Override
    public FeedbackDto addFeedback(int productId, FeedbackDto feedbackDto, Authentication authentication) throws NotAuthorizedException {
        Feedback feedback = new Feedback();
        UserProfile userProfile = userRepository.findByUsername(authentication.getName());
        Product product = productRepository.findById(productId).orElse(null);
        boolean purchase = historyRepository.existsByProductIdAndUserProfileId(
                productId, userProfile.getId());

            if (feedBackRepository.findByProductIdAndAndUserProfileId(productId, userProfile.getId()) == null
                    && purchase) {
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
            product.setAverageRating(String.format("%.1f", average));

        return feedbackMapper.feedbackToFeedbackDto(feedBackRepository.save(feedback));
    }

    /**

     * Returns a list of FeedbackDto objects for a given product ID.
     * @param productId the ID of the product for which to retrieve feedback
     * @return a list of FeedbackDto objects representing the feedback for the given product
     */
    @Override
    public List<FeedbackDto> getFeedbacks(int productId) {
        List<Feedback> feedbackList = feedBackRepository.findAllByProductId(productId);
        return feedbackMapper.feedBackListToFeedbackDtoList(feedbackList);
    }
}
