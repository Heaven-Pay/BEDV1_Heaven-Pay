package com.programmers.heavenpay.payment.dto.response;

public class PaymentCreateResponse {
    private final Long id;

    public PaymentCreateResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static PaymentCreateResponse.PaymentCreateResponseBuilder builder() {
        return new PaymentCreateResponse.PaymentCreateResponseBuilder();
    }

    public static class PaymentCreateResponseBuilder {
        private Long id;

        private PaymentCreateResponseBuilder() {
        }

        public PaymentCreateResponse.PaymentCreateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PaymentCreateResponse build() {
            return new PaymentCreateResponse(this.id);
        }
    }
}
