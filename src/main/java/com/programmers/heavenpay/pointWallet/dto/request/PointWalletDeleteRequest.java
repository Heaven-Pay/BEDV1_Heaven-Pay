package com.programmers.heavenpay.pointWallet.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointWalletDeleteRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    private Long accountId;
}
