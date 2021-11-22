package com.programmers.heavenpay.pointWallet.dto.response;

public class PointWalletDeleteResponse {
    private final Long id;
    private final Integer walletPoint;

    public PointWalletDeleteResponse(Long id, Integer walletPoint) {
        this.id = id;
        this.walletPoint = walletPoint;
    }

    public Long getId() {
        return id;
    }

    public Integer getWalletPoint() {
        return walletPoint;
    }

    public static PointWalletDeleteResponse.PointWalletDeleteResponseBuilder builder() {
        return new PointWalletDeleteResponse.PointWalletDeleteResponseBuilder();
    }

    public static class PointWalletDeleteResponseBuilder {
        private Long id;
        private Integer walletPoint;

        private PointWalletDeleteResponseBuilder() {
        }

        public PointWalletDeleteResponse.PointWalletDeleteResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PointWalletDeleteResponse.PointWalletDeleteResponseBuilder walletPoint(final Integer walletPoint) {
            this.walletPoint = walletPoint;
            return this;
        }

        public PointWalletDeleteResponse build() {
            return new PointWalletDeleteResponse(this.id, this.walletPoint);
        }
    }
}
