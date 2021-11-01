package com.programmers.heavenpay.remittance.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RemittanceGetRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;
}
