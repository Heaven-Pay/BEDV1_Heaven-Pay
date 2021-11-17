package com.programmers.heavenpay.wish.dto.request;

public class WishCreateRequest {
    private Long memberId;

    private Long productId;

    public WishCreateRequest(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }
}