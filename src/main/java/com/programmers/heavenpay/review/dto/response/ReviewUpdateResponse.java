package com.programmers.heavenpay.review.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ReviewUpdateResponse {
    private final Long id;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}
