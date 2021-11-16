package com.programmers.heavenpay.review.dto.response;

import java.time.LocalDateTime;

public class ReviewInfoResponse {
    private final Long id;

    private final int score;

    private final String content;

    private final Long productId;

    private final Long reviewerId;

    private final LocalDateTime modifiedAt;

    public ReviewInfoResponse(Long id, int score, String content, Long productId, Long reviewerId, LocalDateTime modifiedAt) {
        this.id = id;
        this.score = score;
        this.content = content;
        this.productId = productId;
        this.reviewerId = reviewerId;
        this.modifiedAt = modifiedAt;
    }

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

    public static ReviewInfoResponse.ReviewInfoResponseBuilder builder() {
        return new ReviewInfoResponse.ReviewInfoResponseBuilder();
    }

    public static class ReviewInfoResponseBuilder {
        private Long id;

        private int score;

        private String content;

        private Long productId;

        private Long reviewerId;

        private LocalDateTime modifiedAt;

        ReviewInfoResponseBuilder() {
        }

        public ReviewInfoResponse.ReviewInfoResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public ReviewInfoResponse.ReviewInfoResponseBuilder score(final int score) {
            this.score = score;
            return this;
        }

        public ReviewInfoResponse.ReviewInfoResponseBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public ReviewInfoResponse.ReviewInfoResponseBuilder productId(final Long productId) {
            this.productId = productId;
            return this;
        }

        public ReviewInfoResponse.ReviewInfoResponseBuilder reviewerId(final Long reviewerId) {
            this.reviewerId = reviewerId;
            return this;
        }

        public ReviewInfoResponse.ReviewInfoResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public ReviewInfoResponse build() {
            return new ReviewInfoResponse(
                    this.id,
                    this.score,
                    this.content,
                    this.productId,
                    this.reviewerId,
                    this.modifiedAt
            );
        }
    }
}
