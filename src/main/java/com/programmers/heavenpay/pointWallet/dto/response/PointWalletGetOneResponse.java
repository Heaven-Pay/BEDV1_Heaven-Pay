package com.programmers.heavenpay.pointWallet.dto.response;

public class PointWalletGetOneResponse {
    private final Long id;
    private final Integer walletPoint;

    public PointWalletGetOneResponse(Long id, Integer walletPoint) {
        this.id = id;
        this.walletPoint = walletPoint;
    }

    public Long getId() {
        return id;
    }

    public Integer getWalletPoint() {
        return walletPoint;
    }

    public static PointWalletGetOneResponse.PointWalletGetOneResponseBuilder builder() {
        return new PointWalletGetOneResponse.PointWalletGetOneResponseBuilder();
    }

    public static class PointWalletGetOneResponseBuilder {
        private Long id;
        private Integer walletPoint;

        private PointWalletGetOneResponseBuilder() {
        }

        public PointWalletGetOneResponse.PointWalletGetOneResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PointWalletGetOneResponse.PointWalletGetOneResponseBuilder walletPoint(final Integer walletPoint) {
            this.walletPoint = walletPoint;
            return this;
        }

        public PointWalletGetOneResponse build() {
            return new PointWalletGetOneResponse(this.id, this.walletPoint);
        }
    }
}
