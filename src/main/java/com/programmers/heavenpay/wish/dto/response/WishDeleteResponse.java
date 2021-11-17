package com.programmers.heavenpay.wish.dto.response;

public class WishDeleteResponse {
    private final Long id;

    public WishDeleteResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}