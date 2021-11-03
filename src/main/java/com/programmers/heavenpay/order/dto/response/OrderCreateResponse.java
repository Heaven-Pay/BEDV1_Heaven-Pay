package com.programmers.heavenpay.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class OrderCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;
}
