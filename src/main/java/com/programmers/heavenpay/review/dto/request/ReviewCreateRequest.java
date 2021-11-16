package com.programmers.heavenpay.review.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ReviewCreateRequest {
    @Min(value = 1L, message = "reviewerId 는 1보다 커야 합니다.")
    private Long reviewerId;

    @NotBlank(message = "content는 null 이거나 empty일 수 없습니다.")
    private String content;

    @Min(value = 1, message = "score는 1 이상 4이하이어야 합니다.")
    @Max(value = 4, message = "score는 1 이상 4이하이어야 합니다.")
    private int score;

    @Min(value = 1L, message = "productId 는 1보다 커야 합니다.")
    private Long productId;

    public ReviewCreateRequest(Long reviewerId, String content, int score, Long productId) {
        this.reviewerId = reviewerId;
        this.content = content;
        this.score = score;
        this.productId = productId;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public String getContent() {
        return content;
    }

    public int getScore() {
        return score;
    }

    public Long getProductId() {
        return productId;
    }
}
