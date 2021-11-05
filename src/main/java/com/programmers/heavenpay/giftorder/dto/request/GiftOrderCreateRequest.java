package com.programmers.heavenpay.giftorder.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;

@Builder
@Getter
public class GiftOrderCreateRequest {
    @Min(value = 1)
    private int quantity;

    @Min(value = 1L)
    private Long memberId;

    @Min(value = 1L)
    private Long targetMemberId;

    @Min(value = 1L)
    private Long produtId;
}
