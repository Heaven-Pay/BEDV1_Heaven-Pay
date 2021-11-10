package com.programmers.heavenpay.giftorder.dto.request;

import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
public class GiftOrderUpdateRequest {
    @Min(value = 1)
    private int quantity;

    @NotBlank
    private String status;

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }
}
