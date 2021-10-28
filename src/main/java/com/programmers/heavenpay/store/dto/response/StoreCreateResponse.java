package com.programmers.heavenpay.store.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class StoreCreateResponse {
    private final Long id;

    private final LocalDateTime createdAt;
}
