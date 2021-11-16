package com.programmers.heavenpay.review.dto.response;

import lombok.Builder;

@Builder
public class ReviewDeleteResponse {
    private final Long id;

    public Long getId() {
        return id;
    }
}
