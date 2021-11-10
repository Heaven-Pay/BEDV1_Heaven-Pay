package com.programmers.heavenpay.giftorder.dto.response;

import lombok.Builder;

@Builder
public class GiftOrderUpdateResponse {
    private final Long id;

    public Long getId() {
        return id;
    }
}
