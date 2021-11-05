package com.programmers.heavenpay.giftorder.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GiftOrderCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;
}
