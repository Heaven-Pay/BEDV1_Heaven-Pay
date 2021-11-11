package com.programmers.heavenpay.product.dto.response;

import java.time.LocalDateTime;

public class ProductCreateResponse {
    private final Long id;

    private final String s3Path;

    private final LocalDateTime createdAt;

    public ProductCreateResponse(Long id, String s3Path, LocalDateTime createdAt) {
        this.id = id;
        this.s3Path = s3Path;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getS3Path() {
        return s3Path;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
