package com.programmers.heavenpay.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReviewCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;
}
