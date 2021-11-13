package com.programmers.heavenpay.giftorder.entity.vo;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;

import java.util.Arrays;

public enum GiftOrderStatus {
    COMPLETED("사용완료"),
    PAYMENT("결제완료");

    private final String giftOrderStatus;

    GiftOrderStatus(String giftOrderStatus) {
        this.giftOrderStatus = giftOrderStatus;
    }

    public static GiftOrderStatus of(String orderStatus) {
        return Arrays.stream(GiftOrderStatus.values())
                .filter(v -> v.giftOrderStatus.equals(orderStatus))
                .findFirst()
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_GIFT_ORDER_STATUS));
    }

    public String getGiftOrderStatus() {
        return giftOrderStatus;
    }
}
