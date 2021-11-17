package com.programmers.heavenpay.wish.service;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.product.repository.ProductRepository;
import com.programmers.heavenpay.wish.converter.WishConverter;
import com.programmers.heavenpay.wish.dto.request.WishCreateRequest;
import com.programmers.heavenpay.wish.dto.response.WishCreateResponse;
import com.programmers.heavenpay.wish.dto.response.WishDeleteResponse;
import com.programmers.heavenpay.wish.dto.response.WishInfoResponse;
import com.programmers.heavenpay.wish.entity.Wish;
import com.programmers.heavenpay.wish.repository.WishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class WishServiceTest {
    private Long memberId = 1L;
    private Long productId = 2L;
    private int quantity = 3;
    private Long giftOrderId = 3L;
    private String status = "결제완료";
    private Long wishId = 4L;
    private LocalDateTime createdAt = LocalDateTime.now();

    @InjectMocks
    private WishService wishService;

    @Mock
    private WishRepository wishRepository;

    @Mock
    private WishConverter wishConverter;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<Wish> wishPage;

    // ### dto define area ### //
    @Mock
    Page<WishInfoResponse> wishInfoResponsePage;

    private WishCreateResponse wishCreateResponse = new WishCreateResponse(wishId, createdAt);

    private WishCreateRequest wishCreateRequest = new WishCreateRequest(memberId, productId);

    private WishDeleteResponse wishDeleteResponse = new WishDeleteResponse(wishId);
    // ### end of dto define area ### //

    private Member member = Member.builder().build();

    private Product product = Product.builder().build();

    private Wish wish = new Wish(wishId, member, product);

    private Wish wishEntity = new Wish(wishId, member, product);

    @Test
    void 위시리스트_추가_성공_테스트() {
        // given
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(wishConverter.toWishEntity(product, member)).thenReturn(wish);
        when(wishRepository.save(wish)).thenReturn(wishEntity);
        when(wishConverter.toWishCreateResponse(anyLong(), any())).thenReturn(wishCreateResponse);

        // when
        wishService.create(memberId, productId);

        // then
        verify(memberRepository).findById(memberId);
        verify(productRepository).findById(productId);
        verify(wishConverter).toWishEntity(product, member);
        verify(wishRepository).save(wish);
        verify(wishConverter).toWishCreateResponse(anyLong(), any());
    }

    @Test
    void 위시리스트_삭제_성공_테스트() {
        // given
        when(wishConverter.toWishDeleteResponse(anyLong())).thenReturn(wishDeleteResponse);

        // when
        wishService.delete(wishId);

        // then
        verify(wishConverter).toWishDeleteResponse(anyLong());
    }

    @Test
    void 특정사용자의_위시리스트_조회_성공_테스트() { // TODO: 테스트 통과 못함

    }
}