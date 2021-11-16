package com.programmers.heavenpay.follow.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.NotNull;

public class FollowFindRequest {
    @NotNull(message = "아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    protected FollowFindRequest(){

    }

    public FollowFindRequest(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }
}