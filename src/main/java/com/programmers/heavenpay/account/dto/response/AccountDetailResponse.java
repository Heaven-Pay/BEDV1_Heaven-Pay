package com.programmers.heavenpay.account.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AccountDetailResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final String number;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
