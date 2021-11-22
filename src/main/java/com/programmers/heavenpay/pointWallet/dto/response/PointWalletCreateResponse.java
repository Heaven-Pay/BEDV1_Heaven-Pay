package com.programmers.heavenpay.pointWallet.dto.response;

public class PointWalletCreateResponse {
    private final Long id;

    public PointWalletCreateResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static PointWalletCreateResponse.PointWalletCreateResponseBuilder builder() {
        return new PointWalletCreateResponse.PointWalletCreateResponseBuilder();
    }

    public static class PointWalletCreateResponseBuilder {
        private Long id;

        private PointWalletCreateResponseBuilder() {
        }

        public PointWalletCreateResponse.PointWalletCreateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PointWalletCreateResponse build() {
            return new PointWalletCreateResponse(this.id);
        }
    }
}
