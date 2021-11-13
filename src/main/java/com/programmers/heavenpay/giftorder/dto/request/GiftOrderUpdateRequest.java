package com.programmers.heavenpay.giftorder.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class GiftOrderUpdateRequest {
    @Min(value = 1)
    private int quantity;

    @NotBlank
    private String status;

    public GiftOrderUpdateRequest(int quantity, String status) {
        this.quantity = quantity;
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }
}
