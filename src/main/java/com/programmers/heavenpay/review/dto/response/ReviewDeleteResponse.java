package com.programmers.heavenpay.review.dto.response;

public class ReviewDeleteResponse {
    private final Long id;

    public ReviewDeleteResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
