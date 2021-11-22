package com.programmers.heavenpay.pointWallet.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.NotNull;

public class PointWalletDeleteRequest {
    @NotNull(message = "고객 아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @NotNull(message = "계좌정보를 입력하세요.")
    private Long accountId;

    protected PointWalletDeleteRequest() {
    }

    public PointWalletDeleteRequest(Long memberId, Long accountId) {
        this.memberId = memberId;
        this.accountId = accountId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
