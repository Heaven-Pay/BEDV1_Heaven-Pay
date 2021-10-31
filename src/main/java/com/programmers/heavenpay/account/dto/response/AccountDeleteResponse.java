package com.programmers.heavenpay.account.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AccountDeleteResponse {
    private final Long id;
}
