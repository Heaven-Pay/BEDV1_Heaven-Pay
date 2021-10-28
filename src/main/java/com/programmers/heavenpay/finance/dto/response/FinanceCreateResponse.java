package com.programmers.heavenpay.finance.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
@Builder
public class FinanceCreateResponse {
    @NonNull
    private final Long id;

    @NonNull
    private final String financeName;

    @NonNull
    private final String financeType;

    @NonNull
    private final LocalDateTime createdAt;

    @NonNull
    private final LocalDateTime modifiedAt;
}
