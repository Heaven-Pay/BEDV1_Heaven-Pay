package com.programmers.heavenpay.review.converter;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.review.entity.Review;
import com.programmers.heavenpay.store.dto.response.StoreCreateResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewConverter {
    public Review toReviewEntity(String content, int score, Product product, Member reviewer) {
        return Review.builder()
                .content(content)
                .score(score)
                .product(product)
                .reviewer(reviewer)
                .build();
    }

    public StoreCreateResponse toReviewCreateResponse(Long id, LocalDateTime createdDate) {
        return StoreCreateResponse.builder()
                .id(id)
                .createdAt(createdDate)
                .build();
    }
}
