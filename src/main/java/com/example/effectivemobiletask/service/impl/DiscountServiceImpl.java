package com.example.effectivemobiletask.service.impl;

import com.example.effectivemobiletask.dto.DiscountDto;
import com.example.effectivemobiletask.dto.ProductDto;
import com.example.effectivemobiletask.dto.mapper.DiscountMapper;
import com.example.effectivemobiletask.dto.mapper.ProductMapper;
import com.example.effectivemobiletask.model.Discount;
import com.example.effectivemobiletask.model.Product;
import com.example.effectivemobiletask.repository.DiscountRepository;
import com.example.effectivemobiletask.repository.ProductRepository;
import com.example.effectivemobiletask.service.DiscountService;
import com.example.effectivemobiletask.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final SchedulerService schedulerService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public DiscountDto updateDiscount(int productId, DiscountDto discountDto, Authentication authentication) {
        Product product = productRepository.findById(productId).orElse(null);
        assert product != null;
        Discount discount = discountRepository.findById(product.getDiscount().getId()).orElse(null);
        assert discount != null;
        float oldPrice = product.getPrice() * 100 / (100 - discount.getValue());
        product.setPrice(oldPrice);
        discount.setValue(discountDto.getValue());
        discount.setDiscountPeriod(discountDto.getDiscountPeriod());
        float newPrice = product.getPrice() - (product.getPrice() / 100 * discountDto.getValue());
        product.setPrice(newPrice);
        productRepository.save(product);
        return discountMapper.discountToDiscountDto(discountRepository.save(discount));
    }

    @Override
    @Scheduled(cron = "0 0/1 * * * *")
    public void removeExpiredDiscount() {
        for (Discount discount : schedulerService.getDiscounts()) {
            if (discount.getDate().isBefore(LocalDate.now().minusDays(discount.getDiscountPeriod()))) {
                Product product = productRepository.findProductByDiscount(discount);
                float oldPrice = product.getPrice() * 100 / (100 - discount.getValue());
                product.setPrice(oldPrice);
                product.setDiscount(null);
                productRepository.save(product);
                discountRepository.delete(discount);
            }
        }
    }

    @Override
    public ProductDto setDiscount(int productId, DiscountDto discountDto, Authentication authentication) {
        Product product = productRepository.findById(productId).orElse(null);
        assert product != null;
        //discountDto = new DiscountDto();
        Discount discount = discountMapper.discountDtoToDiscount(discountDto);
        discount.setDate(LocalDate.now());
        discountRepository.save(discount);
        product.setDiscount(discount);
        float newPrice = product.getPrice() - (product.getPrice() / 100 * discountDto.getValue());
        product.setPrice(newPrice);
        return productMapper.productToProductDto(productRepository.save(product));
    }

    @Override
    public Collection<ProductDto> setGroupDiscount(String keyWord, DiscountDto discountDto, Authentication authentication) {
        Collection<Product> products = productRepository.findAllByKeywords(keyWord);
        for (Product product : products) {
            Discount discount = discountMapper.discountDtoToDiscount(discountDto);
            discount.setDate(LocalDate.now());
            discountRepository.save(discount);
            product.setDiscount(discount);
            float newPrice = product.getPrice() - (product.getPrice() / 100 * discountDto.getValue());
            product.setPrice(newPrice);
            productMapper.productToProductDto(productRepository.save(product));
        }
        return productMapper.productsToProductDtos(products);
    }
}
