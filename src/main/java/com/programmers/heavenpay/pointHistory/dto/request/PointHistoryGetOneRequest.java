package com.programmers.heavenpay.pointHistory.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

public class PointHistoryGetOneRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    protected PointHistoryGetOneRequest() {
    }

    public PointHistoryGetOneRequest(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
