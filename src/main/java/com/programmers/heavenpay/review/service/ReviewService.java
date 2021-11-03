package com.programmers.heavenpay.review.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.product.repository.ProductRepository;
import com.programmers.heavenpay.review.converter.ReviewConverter;
import com.programmers.heavenpay.review.entity.Review;
import com.programmers.heavenpay.review.repository.ReviewRepository;
import com.programmers.heavenpay.store.dto.response.StoreCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public StoreCreateResponse create(Long reviewerId, String content, int score, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT));

        Member reviewer = memberRepository.findById(reviewerId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID));

        Review review = reviewConverter.toReviewEntity(content, score, product, reviewer);
        Review reviewEntity = reviewRepository.save(review);

        return reviewConverter.toReviewCreateResponse(reviewEntity.getId(), reviewEntity.getCreatedDate());
    }
}
