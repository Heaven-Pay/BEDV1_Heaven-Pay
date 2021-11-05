package com.programmers.heavenpay.payment.converter;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.payment.dto.response.PaymentCreateResponse;
import com.programmers.heavenpay.payment.dto.response.PaymentDeleteResponse;
import com.programmers.heavenpay.payment.entity.Payment;
import com.programmers.heavenpay.pointWallet.entity.PointWallet;
import com.programmers.heavenpay.store.entity.Store;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter {
    public Payment toPaymentEntity(Member member, Store store, PointWallet pointWallet, Integer payPoint) {
        return Payment.builder()
                .member(member)
                .store(store)
                .pointWallet(pointWallet)
                .paymentPoint(payPoint)
                .build();
    }

    public PaymentCreateResponse toPaymentCreateResponse(Payment payment) {
        return PaymentCreateResponse.builder()
                .id(payment.getId())
                .build();
    }

    public PaymentDeleteResponse toPaymentDeleteResponse(Payment payment) {
        return PaymentDeleteResponse.builder()
                .id(payment.getId())
                .payPoint(payment.getPaymentPoint())
                .build();
    }
}
