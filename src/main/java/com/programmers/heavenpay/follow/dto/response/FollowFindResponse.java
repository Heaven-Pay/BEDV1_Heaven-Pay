package com.programmers.heavenpay.follow.dto.response;

import lombok.Getter;

@Getter
public class FollowFindResponse {
    private final Long id;
    private final String name;

    public FollowFindResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
