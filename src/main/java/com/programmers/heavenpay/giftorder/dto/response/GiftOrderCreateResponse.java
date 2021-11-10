package com.programmers.heavenpay.giftorder.dto.response;

import java.time.LocalDateTime;

public class GiftOrderCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;

    public GiftOrderCreateResponse(Long id, LocalDateTime createdAt) {
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
