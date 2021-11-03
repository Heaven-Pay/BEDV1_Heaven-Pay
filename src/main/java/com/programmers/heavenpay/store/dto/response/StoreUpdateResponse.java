package com.programmers.heavenpay.store.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class StoreUpdateResponse {
    private final Long id;

    private final String name;

    private final String type;

    private final String vendorCode;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;
}
