package com.programmers.heavenpay.order.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;

@Builder
@Getter
public class OrderCreateRequest {
    @Min(value = 1)
    private int quantity;

    @Min(value = 1L)
    private Long memberId;

    @Min(value = 1L)
    private Long produtId;
}
