package com.programmers.heavenpay.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReviewUpdateResponse {
    private final Long id;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;
}
