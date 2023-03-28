package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.DiscountDto;
import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.mapper.DiscountMapper;
import com.example.effectivemobiletask.dto.mapper.ProductMapper;
import com.example.effectivemobiletask.exceptions.NotAuthorizedException;
import com.example.effectivemobiletask.model.Discount;
import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.repository.DiscountRepository;
import com.example.effectivemobiletask.repository.ProductRepository;
import com.example.effectivemobiletask.service.DiscountService;
import com.example.effectivemobiletask.service.SchedulerService;
import com.example.effectivemobiletask.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
/**

 * This class implements DiscountService interface and provides methods
 * for managing discounts of products.
 */

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final SchedulerService schedulerService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final SecurityService securityService;

    /**

     * Updates the discount of a product.
     * @param productId The ID of the product whose discount needs to be updated.
     * @param discountDto The new discount details.
     * @param authentication The authentication object representing the current user.
     * @return The updated discount details.
     * @throws NotAuthorizedException If the current user is not authorized to perform this operation.
     */
    @Override
    public DiscountDto updateDiscount(int productId, DiscountDto discountDto, Authentication authentication) throws NotAuthorizedException {
        Product product = productRepository.findById(productId).orElse(null);
        assert product != null;
        Discount discount = discountRepository.findById(product.getDiscount().getId()).orElse(null);
        assert discount != null;
        double oldPrice = product.getPrice() * 100 / (100 - discount.getValue());
        if (securityService.accessAdmin(authentication)) {
            product.setPrice(oldPrice);
            discount.setValue(discountDto.getValue());
            discount.setDiscountPeriod(discountDto.getDiscountPeriod());
            double newPrice = product.getPrice() - (product.getPrice() / 100 * discountDto.getValue());
            product.setPrice(newPrice);
            productRepository.save(product);
        } else {
            throw new NotAuthorizedException("not authorized");
        }
        return discountMapper.discountToDiscountDto(discountRepository.save(discount));
    }

    /**

     * Removes expired discounts from products.
     * This method is scheduled to run every minute using Spring's scheduling feature.
     */
    @Override
    @Scheduled(cron = "0 0/1 * * * *")
    public void removeExpiredDiscount() {
        for (Discount discount : schedulerService.getDiscounts()) {
            if (discount.getDate().isBefore(LocalDate.now().minusDays(discount.getDiscountPeriod()))) {
                Product product = productRepository.findProductByDiscount(discount);
                double oldPrice = product.getPrice() * 100 / (100 - discount.getValue());
                product.setPrice(oldPrice);
                product.setDiscount(null);
                productRepository.save(product);
                discountRepository.delete(discount);
            }
        }
    }

    /**

     * Sets a discount for a product.
     * @param productId The ID of the product for which the discount needs to be set.
     * @param discountDto The discount details.
     * @param authentication The authentication object representing the current user.
     * @return The updated product details.
     * @throws NotAuthorizedException If the current user is not authorized to perform this operation.
     */
    @Override
    public ProductDto setDiscount(int productId, DiscountDto discountDto, Authentication authentication) throws NotAuthorizedException {
        Product product = productRepository.findById(productId).orElse(null);
        assert product != null;
        Discount discount = discountMapper.discountDtoToDiscount(discountDto);
        if (securityService.accessAdmin(authentication)) {
            discount.setDate(LocalDate.now());
            discountRepository.save(discount);
            product.setDiscount(discount);
            double newPrice = product.getPrice() - (product.getPrice() / 100 * discountDto.getValue());
            product.setPrice(newPrice);
        } else {
            throw new NotAuthorizedException("not authorized");
        }
        return productMapper.productToProductDto(productRepository.save(product));
    }

    /**

     * Sets a discount for a group of products that match a given keyword.
     * @param keyWord The keyword used to search for the products.
     * @param discountDto The discount details.
     * @param authentication The authentication object representing the current user.
     * @return A collection of updated product details.
     * @throws NotAuthorizedException If the current user is not authorized to perform this operation.
     */
    @Override
    public Collection<ProductDto> setGroupDiscount(String keyWord, DiscountDto discountDto, Authentication authentication) throws NotAuthorizedException {
        Collection<Product> products = productRepository.findAllByKeywords(keyWord);
        for (Product product : products) {
            Discount discount = discountMapper.discountDtoToDiscount(discountDto);
            if (securityService.accessAdmin(authentication)) {
                discount.setDate(LocalDate.now());
                discountRepository.save(discount);
                product.setDiscount(discount);
                double newPrice = product.getPrice() - (product.getPrice() / 100 * discountDto.getValue());
                product.setPrice(newPrice);
                productMapper.productToProductDto(productRepository.save(product));
            } else {
                throw new NotAuthorizedException("not authorized");
            }
        }
        return productMapper.productsToProductDtos(products);
    }
}
