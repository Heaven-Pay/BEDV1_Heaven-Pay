package com.programmers.heavenpay.pointWallet.dto.response;

public class PointWalletUpdateResponse {
    private final Long id;
    private final Integer walletPoint;

    public PointWalletUpdateResponse(Long id, Integer walletPoint) {
        this.id = id;
        this.walletPoint = walletPoint;
    }

    public Long getId() {
        return id;
    }

    public Integer getWalletPoint() {
        return walletPoint;
    }

    public static PointWalletUpdateResponse.PointWalletUpdateResponseBuilder builder() {
        return new PointWalletUpdateResponse.PointWalletUpdateResponseBuilder();
    }

    public static class PointWalletUpdateResponseBuilder {
        private Long id;
        private Integer walletPoint;

        private PointWalletUpdateResponseBuilder() {
        }

        public PointWalletUpdateResponse.PointWalletUpdateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PointWalletUpdateResponse.PointWalletUpdateResponseBuilder walletPoint(final Integer walletPoint) {
            this.walletPoint = walletPoint;
            return this;
        }

        public PointWalletUpdateResponse build() {
            return new PointWalletUpdateResponse(this.id, this.walletPoint);
        }
    }
}
