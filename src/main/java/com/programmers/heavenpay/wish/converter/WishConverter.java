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
        return new Wish(member, product);
    }

    public WishCreateResponse toWishCreateResponse(Long id, LocalDateTime createdAt) {
        return new WishCreateResponse(id, createdAt);
    }

    public WishInfoResponse toWishInfoResponse(Wish wish) {
        return new WishInfoResponse(wish.getProduct().getId(), wish.getId());
    }

    public WishDeleteResponse toWishDeleteResponse(Long id) {
        return new WishDeleteResponse(id);
    }
}