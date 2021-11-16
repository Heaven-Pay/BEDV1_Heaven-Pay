package com.programmers.heavenpay.finance.dto.response;

import java.time.LocalDateTime;

public class FinanceCreateResponse {
    private final Long id;
    private final String financeName;
    private final String financeType;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public FinanceCreateResponse(final Long id, final String financeName, final String financeType, final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.id = id;
        this.financeName = financeName;
        this.financeType = financeType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static FinanceCreateResponse.FinanceCreateResponseBuilder builder() {
        return new FinanceCreateResponse.FinanceCreateResponseBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getFinanceName() {
        return this.financeName;
    }

    public String getFinanceType() {
        return this.financeType;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return this.modifiedAt;
    }

    public static class FinanceCreateResponseBuilder {
        private Long id;
        private String financeName;
        private String financeType;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        private FinanceCreateResponseBuilder() {
        }

        public FinanceCreateResponse.FinanceCreateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public FinanceCreateResponse.FinanceCreateResponseBuilder financeName(final String financeName) {
            this.financeName = financeName;
            return this;
        }

        public FinanceCreateResponse.FinanceCreateResponseBuilder financeType(final String financeType) {
            this.financeType = financeType;
            return this;
        }

        public FinanceCreateResponse.FinanceCreateResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FinanceCreateResponse.FinanceCreateResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public FinanceCreateResponse build() {
            return new FinanceCreateResponse(this.id, this.financeName, this.financeType, this.createdAt, this.modifiedAt);
        }
    }
}
