package com.programmers.heavenpay.store.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class StoreCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;

    public StoreCreateResponse(Long id, LocalDateTime createdAt) {
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
