package com.programmers.heavenpay.follow.dto.response;

import com.programmers.heavenpay.follow.entity.vo.FollowStatus;

public class FollowResponse {
    private final String followStatus;
    private final String follower;

    public FollowResponse(FollowStatus followStatus, String follower) {
        this.followStatus = followStatus.name();
        this.follower = follower;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public String getFollower() {
        return follower;
    }
}