package com.programmers.heavenpay.account.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class AccountDeleteRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;
}
