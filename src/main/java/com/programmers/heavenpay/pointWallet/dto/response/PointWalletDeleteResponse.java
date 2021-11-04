package com.programmers.heavenpay.pointWallet.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PointWalletDeleteResponse {
    private final Long id;
    private final Integer walletPoint;
}
