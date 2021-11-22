package com.programmers.heavenpay.payment.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

public class PaymentCreateRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    private Long storeId;

    private Long pointWalletId;

    private Integer payPoint;

    protected PaymentCreateRequest() {
    }

    public PaymentCreateRequest(Long memberId, Long storeId, Long pointWalletId, Integer payPoint) {
        this.memberId = memberId;
        this.storeId = storeId;
        this.pointWalletId = pointWalletId;
        this.payPoint = payPoint;
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

    public Integer getPayPoint() {
        return payPoint;
    }
}
