package com.programmers.heavenpay.finance.dto.response;

import java.time.LocalDateTime;

public class FinanceDetailResponse {
    private final Long id;
    private final String financeName;
    private final String financeType;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public FinanceDetailResponse(final Long id, final String financeName, final String financeType, final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.id = id;
        this.financeName = financeName;
        this.financeType = financeType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static FinanceDetailResponse.FinanceDetailResponseBuilder builder() {
        return new FinanceDetailResponse.FinanceDetailResponseBuilder();
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

    public static class FinanceDetailResponseBuilder {
        private Long id;
        private String financeName;
        private String financeType;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        private FinanceDetailResponseBuilder() {
        }

        public FinanceDetailResponse.FinanceDetailResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public FinanceDetailResponse.FinanceDetailResponseBuilder financeName(final String financeName) {
            this.financeName = financeName;
            return this;
        }

        public FinanceDetailResponse.FinanceDetailResponseBuilder financeType(final String financeType) {
            this.financeType = financeType;
            return this;
        }

        public FinanceDetailResponse.FinanceDetailResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FinanceDetailResponse.FinanceDetailResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public FinanceDetailResponse build() {
            return new FinanceDetailResponse(this.id, this.financeName, this.financeType, this.createdAt, this.modifiedAt);
        }
    }
}

