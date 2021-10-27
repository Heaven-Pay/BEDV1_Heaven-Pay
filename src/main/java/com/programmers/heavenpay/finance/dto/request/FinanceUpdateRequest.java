package com.programmers.heavenpay.finance.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FinanceUpdateRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    private String financeName;

    private String financeType;
}
