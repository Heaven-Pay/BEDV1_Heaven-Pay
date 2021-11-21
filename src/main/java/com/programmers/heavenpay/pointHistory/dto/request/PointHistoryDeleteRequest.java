package com.programmers.heavenpay.pointHistory.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

public class PointHistoryDeleteRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    protected PointHistoryDeleteRequest() {
    }

    public PointHistoryDeleteRequest(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
