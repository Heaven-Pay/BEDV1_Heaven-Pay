package com.programmers.heavenpay.order.entity.vo;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {
    COMPLETED("사용완료"),
    PAYMENT("결제완료");

    private final String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public static OrderStatus of(String orderStatus) {
        return Arrays.stream(OrderStatus.values())
                .filter(v -> v.orderStatus.equals(orderStatus))
                .findFirst()
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_ORDER_STATUS));
    }
}
