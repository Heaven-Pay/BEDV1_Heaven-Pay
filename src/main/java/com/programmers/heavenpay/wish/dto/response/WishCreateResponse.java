package com.programmers.heavenpay.wish.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class WishCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;
}