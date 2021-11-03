package com.programmers.heavenpay.wish.dto.requset;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WishCreateRequest {
    private Long memberId;

    private Long productId;
}
