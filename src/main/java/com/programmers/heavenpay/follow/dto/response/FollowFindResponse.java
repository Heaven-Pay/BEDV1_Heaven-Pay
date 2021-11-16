package com.programmers.heavenpay.follow.dto.response;

public class FollowFindResponse {
    private final Long id;
    private final String name;

    public FollowFindResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}