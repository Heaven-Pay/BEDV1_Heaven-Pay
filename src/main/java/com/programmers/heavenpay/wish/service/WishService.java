package com.programmers.heavenpay.wish.service;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.member.repository.MemberRepository;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.product.repository.ProductRepository;
import com.programmers.heavenpay.wish.converter.WishConverter;
import com.programmers.heavenpay.wish.dto.response.WishCreateResponse;
import com.programmers.heavenpay.wish.dto.response.WishDeleteResponse;
import com.programmers.heavenpay.wish.dto.response.WishInfoResponse;
import com.programmers.heavenpay.wish.entity.Wish;
import com.programmers.heavenpay.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishRepository;
    private final WishConverter wishConverter;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public WishCreateResponse create(Long memberId, Long productId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_PRODUCT));

        Wish wish = wishConverter.toWishEntity(product, member);
        Wish wishEntity = wishRepository.save(wish);

        return wishConverter.toWishCreateResponse(wishEntity.getId(), wishEntity.getCreatedDate());
    }

    @Transactional(readOnly = true)
    public Page<WishInfoResponse> findAllByPages(Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_MEMBER_ID));

        Page<Wish> wishPage = wishRepository.findAllByMember(member, pageable);

        return wishPage.map(wishConverter::toWishInfoResponse);
    }

    @Transactional
    public WishDeleteResponse delete(Long wishId) {
        //TODO: member에서도 wish 삭제하기
        wishRepository.deleteById(wishId);

        return wishConverter.toWishDeleteResponse(wishId);
    }
}