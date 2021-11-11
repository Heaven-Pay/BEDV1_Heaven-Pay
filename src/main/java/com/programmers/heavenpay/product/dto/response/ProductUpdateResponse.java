package com.programmers.heavenpay.product.dto.response;

import java.time.LocalDateTime;

public class ProductUpdateResponse {
    private final Long id;

    private final String s3Path;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public ProductUpdateResponse(Long id, String s3Path, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.s3Path = s3Path;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
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

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public static ProductUpdateResponse.ProductUpdateResponseBuilder builder() {
        return new ProductUpdateResponse.ProductUpdateResponseBuilder();
    }

    public static class ProductUpdateResponseBuilder {
        private Long id;

        private String s3Path;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        ProductUpdateResponseBuilder() {
        }

        public ProductUpdateResponse.ProductUpdateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public ProductUpdateResponse.ProductUpdateResponseBuilder s3Path(final String s3Path) {
            this.s3Path = s3Path;
            return this;
        }

        public ProductUpdateResponse.ProductUpdateResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductUpdateResponse.ProductUpdateResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public ProductUpdateResponse build() {
            return new ProductUpdateResponse(this.id, this.s3Path, this.createdAt, this.modifiedAt);
        }
    }
}
