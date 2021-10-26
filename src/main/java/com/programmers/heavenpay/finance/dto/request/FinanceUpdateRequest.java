package com.programmers.heavenpay.finance.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class FinanceUpdateRequest {
    @ArbitraryAuthenticationPrincipal
    @NotBlank
    private Long id;

    private String financeName;

    private String financeType;
}
