package com.programmers.heavenpay.giftorder.dto.request;

import javax.validation.constraints.Min;

public class GiftOrderCreateRequest {
    @Min(value = 1)
    private int quantity;

    @Min(value = 1L)
    private Long memberId;

    @Min(value = 1L)
    private Long targetMemberId;

    @Min(value = 1L)
    private Long produtId;

    public GiftOrderCreateRequest(int quantity, Long memberId, Long targetMemberId, Long produtId) {
        this.quantity = quantity;
        this.memberId = memberId;
        this.targetMemberId = targetMemberId;
        this.produtId = produtId;
    }

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
