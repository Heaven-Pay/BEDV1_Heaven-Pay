package com.programmers.heavenpay.account.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
@Builder
public class AccountDeleteRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;
}
