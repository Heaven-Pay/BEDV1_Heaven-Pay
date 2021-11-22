package com.programmers.heavenpay.payment.dto.response;

public class PaymentDeleteResponse {
    private final Long id;
    private final Integer payPoint;

    public PaymentDeleteResponse(Long id, Integer payPoint) {
        this.id = id;
        this.payPoint = payPoint;
    }

    public Long getId() {
        return id;
    }

    public Integer getPayPoint() {
        return payPoint;
    }

    public static PaymentDeleteResponse.PaymentDeleteResponseBuilder builder() {
        return new PaymentDeleteResponse.PaymentDeleteResponseBuilder();
    }

    public static class PaymentDeleteResponseBuilder {
        private Long id;
        private Integer payPoint;

        private PaymentDeleteResponseBuilder() {
        }

        public PaymentDeleteResponse.PaymentDeleteResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PaymentDeleteResponse.PaymentDeleteResponseBuilder payPoint(final Integer payPoint) {
            this.payPoint = payPoint;
            return this;
        }

        public PaymentDeleteResponse build() {
            return new PaymentDeleteResponse(this.id, this.payPoint);
        }
    }
}
