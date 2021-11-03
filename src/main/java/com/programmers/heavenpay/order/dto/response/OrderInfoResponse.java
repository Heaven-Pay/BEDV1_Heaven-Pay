package com.programmers.heavenpay.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class OrderInfoResponse {
    private final Long id;

    private final int quantity;

    private final String status;

    private final Long productId;

    private final LocalDateTime createdAt;

    private final LocalDateTime mdifiedAt;
}
