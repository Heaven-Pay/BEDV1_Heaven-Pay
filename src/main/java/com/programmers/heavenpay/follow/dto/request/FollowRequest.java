package com.programmers.heavenpay.follow.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowRequest {
    @ArbitraryAuthenticationPrincipal
    private Long id;
    private Long followerId;
}
