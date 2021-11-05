package com.programmers.heavenpay.pointWallet.dto.response;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PointWalletCreateResponse {
    private final Long id;
}
