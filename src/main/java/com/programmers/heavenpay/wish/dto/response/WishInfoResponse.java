package com.programmers.heavenpay.wish.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WishInfoResponse {
    private final Long id;

    private final Long productId;
}