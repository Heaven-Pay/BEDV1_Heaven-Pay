package com.programmers.heavenpay.account.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
@Builder
public class AccountCreateResponse {
    @NonNull
    private final Long id;

    @NonNull
    private final String title;

    private final String description;

    @NonNull
    private final String number;

    @NonNull
    private final LocalDateTime createdAt;

    @NonNull
    private final LocalDateTime modifiedAt;
}
