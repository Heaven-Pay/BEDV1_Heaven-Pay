package com.programmers.heavenpay.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReviewInfoResponse {
    private final Long id;

    private final int score;

    private final String content;

    private final Long productId;

    private final Long reviewerId;

    private final LocalDateTime modifiedAt;
}
