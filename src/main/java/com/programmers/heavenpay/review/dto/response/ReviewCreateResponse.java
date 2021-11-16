package com.programmers.heavenpay.review.dto.response;

import java.time.LocalDateTime;

public class ReviewCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;

    public ReviewCreateResponse(Long id, LocalDateTime createdAt) {
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
