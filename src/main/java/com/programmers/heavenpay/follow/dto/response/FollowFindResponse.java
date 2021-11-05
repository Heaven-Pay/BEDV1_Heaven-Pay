package com.programmers.heavenpay.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class FollowFindResponse {
    private final Long id;
    private final String name;

    public FollowFindResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
