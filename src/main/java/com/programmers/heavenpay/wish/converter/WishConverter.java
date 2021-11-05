package com.programmers.heavenpay.wish.converter;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.product.entitiy.Product;
import com.programmers.heavenpay.wish.dto.response.WishCreateResponse;
import com.programmers.heavenpay.wish.dto.response.WishDeleteResponse;
import com.programmers.heavenpay.wish.dto.response.WishInfoResponse;
import com.programmers.heavenpay.wish.entity.Wish;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WishConverter {
    public Wish toWishEntity(Product product, Member member) {
        return Wish.builder()
                .member(member)
                .product(product)
                .build();
    }

    public WishCreateResponse toWishCreateResponse(Long id, LocalDateTime createdAt) {
        return WishCreateResponse.builder()
                .id(id)
                .createdAt(createdAt)
                .build();
    }

    public WishInfoResponse toWishInfoResponse(Wish wish) {
        return WishInfoResponse.builder()
                .productId(wish.getProduct().getId())
                .id(wish.getId())
                .build();
    }

    public WishDeleteResponse toWishDeleteResponse(Long id) {
        return WishDeleteResponse.builder()
                .id(id)
                .build();
    }
}