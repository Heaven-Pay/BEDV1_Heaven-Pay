package com.programmers.heavenpay.review.converter;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.review.dto.response.ReviewCreateResponse;
import com.programmers.heavenpay.review.dto.response.ReviewDeleteResponse;
import com.programmers.heavenpay.review.dto.response.ReviewInfoResponse;
import com.programmers.heavenpay.review.dto.response.ReviewUpdateResponse;
import com.programmers.heavenpay.review.entity.Review;
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

    public ReviewCreateResponse toReviewCreateResponse(Long id, LocalDateTime createdDate) {
        return ReviewCreateResponse.builder()
                .id(id)
                .createdAt(createdDate)
                .build();
    }

    public ReviewUpdateResponse toReviewUpdateResponse(Review review) {
        return ReviewUpdateResponse.builder()
                .id(review.getId())
                .createdAt(review.getCreatedDate())
                .modifiedAt(review.getModifiedDate())
                .build();
    }

    public ReviewInfoResponse toReviewInfoResponse(Review review) {
        return ReviewInfoResponse.builder()
                .content(review.getContent())
                .id(review.getId())
                .modifiedAt(review.getModifiedDate())
                .score(review.getScore())
                .reviewerId(review.getReviewer().getId())
                .productId(review.getProduct().getId())
                .build();
    }

    public ReviewDeleteResponse toStoreDeleteResponse(Review review) {
        return ReviewDeleteResponse.builder()
                .id(review.getId())
                .build();
    }
}
