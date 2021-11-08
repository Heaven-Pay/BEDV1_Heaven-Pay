package com.programmers.heavenpay.finance.dto.response;

import java.time.LocalDateTime;

public class FinanceDeleteResponse {
    private final Long id;
    private final String financeName;
    private final String financeType;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public FinanceDeleteResponse(final Long id, final String financeName, final String financeType, final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.id = id;
        this.financeName = financeName;
        this.financeType = financeType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static FinanceDeleteResponse.FinanceDeleteResponseBuilder builder() {
        return new FinanceDeleteResponse.FinanceDeleteResponseBuilder();
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

    public static class FinanceDeleteResponseBuilder {
        private Long id;
        private String financeName;
        private String financeType;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        FinanceDeleteResponseBuilder() {
        }

        public FinanceDeleteResponse.FinanceDeleteResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public FinanceDeleteResponse.FinanceDeleteResponseBuilder financeName(final String financeName) {
            this.financeName = financeName;
            return this;
        }

        public FinanceDeleteResponse.FinanceDeleteResponseBuilder financeType(final String financeType) {
            this.financeType = financeType;
            return this;
        }

        public FinanceDeleteResponse.FinanceDeleteResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FinanceDeleteResponse.FinanceDeleteResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public FinanceDeleteResponse build() {
            return new FinanceDeleteResponse(this.id, this.financeName, this.financeType, this.createdAt, this.modifiedAt);
        }

        public String toString() {
            return "FinanceDeleteResponse.FinanceDeleteResponseBuilder(id=" + this.id + ", financeName=" + this.financeName + ", financeType=" + this.financeType + ", createdAt=" + this.createdAt + ", modifiedAt=" + this.modifiedAt + ")";
        }
    }
}