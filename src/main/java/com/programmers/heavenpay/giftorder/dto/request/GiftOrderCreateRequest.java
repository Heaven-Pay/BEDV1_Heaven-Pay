package com.programmers.heavenpay.giftorder.dto.request;

import lombok.Builder;

import javax.validation.constraints.Min;

@Builder
public class GiftOrderCreateRequest {
    @Min(value = 1)
    private int quantity;

    @Min(value = 1L)
    private Long memberId;

    @Min(value = 1L)
    private Long targetMemberId;

    @Min(value = 1L)
    private Long produtId;

    public int getQuantity() {
        return quantity;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getTargetMemberId() {
        return targetMemberId;
    }

    public Long getProdutId() {
        return produtId;
    }
}
