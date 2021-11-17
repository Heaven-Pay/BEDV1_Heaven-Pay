package com.programmers.heavenpay.wish.dto.response;

import java.time.LocalDateTime;

public class WishCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;

    public WishCreateResponse(Long id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}