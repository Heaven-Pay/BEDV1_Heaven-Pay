package com.programmers.heavenpay.store.dto.response;

import java.time.LocalDateTime;

public class StoreCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;

    public StoreCreateResponse(Long id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static StoreCreateResponse.StoreCreateResponseBuilder builder() {
        return new StoreCreateResponse.StoreCreateResponseBuilder();
    }

    public static class StoreCreateResponseBuilder {
        private Long id;

        private LocalDateTime createdAt;

        StoreCreateResponseBuilder() {
        }

        public StoreCreateResponse.StoreCreateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public StoreCreateResponse.StoreCreateResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StoreCreateResponse build() {
            return new StoreCreateResponse(this.id, this.createdAt);
        }
    }
}
