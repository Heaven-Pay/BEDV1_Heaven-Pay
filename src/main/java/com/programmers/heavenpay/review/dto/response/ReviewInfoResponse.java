package com.programmers.heavenpay.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
public class ReviewInfoResponse {
    private final Long id;

    private final int score;

    private final String content;

    private final Long productId;

    private final Long reviewerId;

    private final LocalDateTime modifiedAt;

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getContent() {
        return content;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}
