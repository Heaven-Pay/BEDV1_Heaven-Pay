package com.programmers.heavenpay.payment.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

public class PaymentDeleteRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    private Long storeId;

    private Long pointWalletId;

    protected PaymentDeleteRequest() {
    }

    public PaymentDeleteRequest(Long memberId, Long storeId, Long pointWalletId) {
        this.memberId = memberId;
        this.storeId = storeId;
        this.pointWalletId = pointWalletId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public Long getPointWalletId() {
        return pointWalletId;
    }
}
