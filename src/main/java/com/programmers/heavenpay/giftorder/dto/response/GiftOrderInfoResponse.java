package com.programmers.heavenpay.giftorder.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class GiftOrderInfoResponse {
    private final Long id;

    private final int quantity;

    private final String status;

    private final Long productId;

    private final LocalDateTime createdAt;

    private final LocalDateTime mdifiedAt;

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

    public LocalDateTime getMdifiedAt() {
        return mdifiedAt;
    }
}
