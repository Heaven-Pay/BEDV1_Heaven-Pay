package com.programmers.heavenpay.review.dto.response;

import java.time.LocalDateTime;

public class ReviewUpdateResponse {
    private final Long id;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public ReviewUpdateResponse(Long id, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

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
