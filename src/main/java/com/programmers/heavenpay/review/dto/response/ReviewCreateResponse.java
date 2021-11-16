package com.programmers.heavenpay.review.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ReviewCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
