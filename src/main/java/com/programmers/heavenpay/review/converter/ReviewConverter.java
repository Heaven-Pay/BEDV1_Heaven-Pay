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
        return new ReviewCreateResponse(id, createdDate);
    }

    public ReviewUpdateResponse toReviewUpdateResponse(Review review) {
        return new ReviewUpdateResponse(review.getId(), review.getCreatedDate(), review.getModifiedDate());
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
        return new ReviewDeleteResponse(review.getId());
    }
}
