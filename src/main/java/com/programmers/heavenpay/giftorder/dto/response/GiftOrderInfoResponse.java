package com.programmers.heavenpay.giftorder.dto.response;

import java.time.LocalDateTime;

public class GiftOrderInfoResponse {
    private final Long id;

    private final int quantity;

    private final String status;

    private final Long productId;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public GiftOrderInfoResponse(Long id, int quantity, String status, Long productId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;
        this.productId = productId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public Long getProductId() {
        return productId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public static GiftOrderInfoResponse.GiftOrderInfoResponseBuilder builder() {
        return new GiftOrderInfoResponse.GiftOrderInfoResponseBuilder();
    }

    public static class GiftOrderInfoResponseBuilder {
        private Long id;

        private int quantity;

        private String status;

        private Long productId;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        GiftOrderInfoResponseBuilder() {
        }

        public GiftOrderInfoResponse.GiftOrderInfoResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public GiftOrderInfoResponse.GiftOrderInfoResponseBuilder quantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public GiftOrderInfoResponse.GiftOrderInfoResponseBuilder status(final String status) {
            this.status = status;
            return this;
        }

        public GiftOrderInfoResponse.GiftOrderInfoResponseBuilder productId(final Long productId) {
            this.productId = productId;
            return this;
        }

        public GiftOrderInfoResponse.GiftOrderInfoResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public GiftOrderInfoResponse.GiftOrderInfoResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public GiftOrderInfoResponse build() {
            return new GiftOrderInfoResponse(this.id, this.quantity, this.status, this.productId, this.createdAt, this.modifiedAt);
        }
    }
}
