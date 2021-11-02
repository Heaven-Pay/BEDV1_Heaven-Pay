package com.programmers.heavenpay.remittance.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RemittanceGetRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;
}
