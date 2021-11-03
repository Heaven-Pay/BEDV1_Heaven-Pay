package com.programmers.heavenpay.review.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.product.repository.ProductRepository;
import com.programmers.heavenpay.review.converter.ReviewConverter;
import com.programmers.heavenpay.review.dto.response.ReviewCreateResponse;
import com.programmers.heavenpay.review.dto.response.ReviewDeleteResponse;
import com.programmers.heavenpay.review.dto.response.ReviewInfoResponse;
import com.programmers.heavenpay.review.dto.response.ReviewUpdateResponse;
import com.programmers.heavenpay.review.entity.Review;
import com.programmers.heavenpay.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ReviewCreateResponse create(Long reviewerId, String content, int score, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT));

        Member reviewer = memberRepository.findById(reviewerId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID));

        Review review = reviewConverter.toReviewEntity(content, score, product, reviewer);
        Review reviewEntity = reviewRepository.save(review);

        return reviewConverter.toReviewCreateResponse(reviewEntity.getId(), reviewEntity.getCreatedDate());
    }

    @Transactional
    public ReviewUpdateResponse update(Long reviewId, String content, int score) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_REVIEW));

        review.updateInfo(content, score);

        return reviewConverter.toReviewUpdateResponse(review);
    }

    @Transactional(readOnly = true)
    public ReviewInfoResponse findById(Long productId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_REVIEW));

        if(review.getProduct().getId() != productId){
            //TODO: 예외 발생, 로직 위치 결정
        }

        return reviewConverter.toReviewInfoResponse(review);
    }

    @Transactional(readOnly = true)
    public Page<ReviewInfoResponse> findAllByPages(Pageable pageable, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT_ID));

        Page<Review> allByProduct = reviewRepository.findAllByProduct(product, pageable);

        return allByProduct.map(reviewConverter::toReviewInfoResponse);
    }

    @Transactional
    public ReviewDeleteResponse delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_REVIEW));

        review.deleteFromProduct();
        reviewRepository.delete(review);

        return reviewConverter.toStoreDeleteResponse(review);
    }
}
