package com.programmers.heavenpay.follow.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.NotNull;

public class FollowRequest {
    @NotNull(message = "아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private Long id;

    @NotNull(message = "아이디를 입력하세요.")
    private Long followerId;

    protected FollowRequest() {

    }

    public FollowRequest(Long id, Long followerId) {
        this.id = id;
        this.followerId = followerId;
    }

    public Long getId() {
        return id;
    }

    public Long getFollowerId() {
        return followerId;
    }
}