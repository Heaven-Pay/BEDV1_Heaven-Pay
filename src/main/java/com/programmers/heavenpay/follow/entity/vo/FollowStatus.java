package com.programmers.heavenpay.follow.entity.vo;

public enum FollowStatus {
    FOLLOWING("팔로우"),
    UNFOLLOWING("언팔로우");

    private final String status;

    FollowStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}