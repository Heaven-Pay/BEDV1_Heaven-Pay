package com.programmers.heavenpay.finance.dto.response;

import java.time.LocalDateTime;

public class FinanceUpdateResponse {
    private final Long id;
    private final String financeName;
    private final String financeType;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private FinanceUpdateResponse(final Long id, final String financeName, final String financeType, final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.id = id;
        this.financeName = financeName;
        this.financeType = financeType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static FinanceUpdateResponse.FinanceUpdateResponseBuilder builder() {
        return new FinanceUpdateResponse.FinanceUpdateResponseBuilder();
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

    public static class FinanceUpdateResponseBuilder {
        private Long id;
        private String financeName;
        private String financeType;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        private FinanceUpdateResponseBuilder() {
        }

        public FinanceUpdateResponse.FinanceUpdateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public FinanceUpdateResponse.FinanceUpdateResponseBuilder financeName(final String financeName) {
            this.financeName = financeName;
            return this;
        }

        public FinanceUpdateResponse.FinanceUpdateResponseBuilder financeType(final String financeType) {
            this.financeType = financeType;
            return this;
        }

        public FinanceUpdateResponse.FinanceUpdateResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FinanceUpdateResponse.FinanceUpdateResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public FinanceUpdateResponse build() {
            return new FinanceUpdateResponse(this.id, this.financeName, this.financeType, this.createdAt, this.modifiedAt);
        }
    }
}