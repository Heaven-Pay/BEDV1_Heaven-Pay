package com.programmers.heavenpay.wish.dto.response;

public class WishInfoResponse {
    private final Long id;

    private final Long productId;

    public WishInfoResponse(Long id, Long productId) {
        this.id = id;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }
}