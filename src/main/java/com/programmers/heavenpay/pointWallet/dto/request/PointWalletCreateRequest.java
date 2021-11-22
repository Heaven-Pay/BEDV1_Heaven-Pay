package com.programmers.heavenpay.pointWallet.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class PointWalletCreateRequest {
    @NotNull(message = "고객 아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @NotNull(message = "계좌정보를 입력하세요.")
    private Long accountId;

    @PositiveOrZero
    private Integer walletPoint;

    protected PointWalletCreateRequest() {
    }

    public PointWalletCreateRequest(Long memberId, Long accountId, Integer walletPoint) {
        this.memberId = memberId;
        this.accountId = accountId;
        this.walletPoint = walletPoint;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Integer getWalletPoint() {
        return walletPoint;
    }
}
