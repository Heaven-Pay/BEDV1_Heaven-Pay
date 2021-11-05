package com.programmers.heavenpay.follow.dto.response;

import com.programmers.heavenpay.follow.entity.vo.FollowStatus;
import lombok.Getter;

@Getter
public class FollowResponse {
    private final String followStatus;
    private final String follower;

    public FollowResponse(FollowStatus followStatus, String follower) {
        this.followStatus = followStatus.name();
        this.follower = follower;
    }
}
