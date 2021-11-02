package com.programmers.heavenpay.account.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccountDeleteRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;
}
