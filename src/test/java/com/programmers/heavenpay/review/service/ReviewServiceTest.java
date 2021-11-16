package com.programmers.heavenpay.review.service;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    private Long reviewerId = 1L;
    private String content = "contennt1";
    private int score = 3;
    private Long productId = 2L;
    private Long reviewId = 3L;

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewConverter reviewConverter;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<Review> reviewPage;

    Page<ReviewInfoResponse> reviewInfoResponsePage;

    private Product product = Product.builder()
            .id(productId)
            .build();

    private Member reviewer = Member.builder()
            .id(reviewerId)
            .build();

    private Review review = Review.builder()
            .id(reviewId)
            .product(product)
            .build();

    private Review reviewEntity = Review.builder()
            .id(reviewId)
            .product(product)
            .build();

    // dto
    private ReviewCreateResponse reviewCreateResponse = new ReviewCreateResponse(reviewerId, LocalDateTime.now());
    private ReviewUpdateResponse reviewUpdateResponse = new ReviewUpdateResponse(reviewId, LocalDateTime.now(), LocalDateTime.now());
    private ReviewInfoResponse reviewInfoResponse = ReviewInfoResponse.builder().build();
    private ReviewDeleteResponse reviewDeleteResponse = new ReviewDeleteResponse(reviewerId);

    @Test
    void 리뷰_신규_추가_성공_테스트() {
        // given
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(reviewer));
        when(reviewConverter.toReviewEntity(anyString(), anyInt(), any(), any())).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(reviewEntity);
        when(reviewConverter.toReviewCreateResponse(reviewEntity.getId(), reviewEntity.getCreatedDate())).thenReturn(reviewCreateResponse);

        // when
        reviewService.create(reviewerId, content, score, productId);

        // then
        verify(productRepository).findById(anyLong());
        verify(memberRepository).findById(anyLong());
        verify(reviewConverter).toReviewEntity(anyString(), anyInt(), any(), any());
        verify(reviewRepository).save(review);
        verify(reviewConverter).toReviewCreateResponse(anyLong(), any());
    }

    @Test
    void 리뷰_수정_성공_테스트(){
        //given
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewConverter.toReviewUpdateResponse(review)).thenReturn(reviewUpdateResponse);

        //when
        reviewService.update(reviewId, content, score);

        //then
        verify(reviewRepository).findById(reviewId);
        verify(reviewConverter).toReviewUpdateResponse(review);
    }

    @Test
    void 리뷰ID로조회_성공_테스트(){
        //given
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewConverter.toReviewInfoResponse(review)).thenReturn(reviewInfoResponse);

        //when
        reviewService.findById(productId, reviewId);

        //then
        verify(reviewRepository).findById(reviewId);
        verify(reviewConverter).toReviewInfoResponse(review);
    }

    @Test
    void 특정_상품의_리뷰_전체_조회_성공_테스트(){   //TODO: 테스트 통과 못함
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(reviewRepository.findAllByProduct(product, pageable)).thenReturn(reviewPage);
        when(reviewConverter.toReviewInfoResponse(review)).thenReturn(reviewInfoResponse);
        when(reviewPage.map(reviewConverter::toReviewInfoResponse)).thenReturn(reviewInfoResponsePage);

        reviewService.findAllByPages(pageable, productId);

        verify(productRepository).findById(productId);
        verify(reviewRepository).findAllByProduct(product, pageable);
        verify(reviewConverter).toReviewInfoResponse(review);
        verify(reviewPage.map(reviewConverter::toReviewInfoResponse));
    }

    @Test
    void 리뷰_삭제_성공_테스트(){  //TODO: 테스트 통과 못함
        //given
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewConverter.toStoreDeleteResponse(review)).thenReturn(reviewDeleteResponse);

        //when
        review.makeRelationWithProduct(product);
        reviewService.delete(reviewId);

        //then
        verify(reviewRepository).findById(reviewId);
        verify(reviewConverter).toStoreDeleteResponse(review);
    }
}