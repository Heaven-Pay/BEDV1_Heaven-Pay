package com.programmers.heavenpay.finance.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
@Builder
public class FinanceDeleteResponse {
    @NonNull
    private Long id;
    @NonNull
    private String financeName;
    @NonNull
    private String financeType;
    @NonNull
    private final LocalDateTime createdAt;
    @NonNull
    private final LocalDateTime modifiedAt;
}
