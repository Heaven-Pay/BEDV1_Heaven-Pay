package com.programmers.heavenpay.payment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentDeleteResponse {
    private final Long id;
    private final Integer payPoint;
}
