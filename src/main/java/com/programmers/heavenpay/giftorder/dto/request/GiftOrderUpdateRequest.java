package com.programmers.heavenpay.giftorder.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class GiftOrderUpdateRequest {
    @Min(value = 1)
    private int quantity;

    @NotBlank
    private String status;
}
