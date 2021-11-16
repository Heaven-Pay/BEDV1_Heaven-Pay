package com.programmers.heavenpay.remittance.dto.response;

import java.time.LocalDateTime;

public class RemittanceDetailAllResponse {
    private final Long remittanceId;
    private final String memberName;
    private final String financeName;
    private final String remittanceName;
    private final Integer remittanceMoney;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private RemittanceDetailAllResponse(final Long remittanceId, final String memberName, final String financeName, final String remittanceName, final Integer remittanceMoney, final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.remittanceId = remittanceId;
        this.memberName = memberName;
        this.financeName = financeName;
        this.remittanceName = remittanceName;
        this.remittanceMoney = remittanceMoney;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static RemittanceDetailAllResponse.RemittanceDetailAllResponseBuilder builder() {
        return new RemittanceDetailAllResponse.RemittanceDetailAllResponseBuilder();
    }

    public Long getRemittanceId() {
        return this.remittanceId;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public String getFinanceName() {
        return this.financeName;
    }

    public String getRemittanceName() {
        return this.remittanceName;
    }

    public Integer getRemittanceMoney() {
        return this.remittanceMoney;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return this.modifiedAt;
    }

    public static class RemittanceDetailAllResponseBuilder {
        private Long remittanceId;
        private String memberName;
        private String financeName;
        private String remittanceName;
        private Integer remittanceMoney;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        private RemittanceDetailAllResponseBuilder() {
        }

        public RemittanceDetailAllResponse.RemittanceDetailAllResponseBuilder remittanceId(final Long remittanceId) {
            this.remittanceId = remittanceId;
            return this;
        }

        public RemittanceDetailAllResponse.RemittanceDetailAllResponseBuilder memberName(final String memberName) {
            this.memberName = memberName;
            return this;
        }

        public RemittanceDetailAllResponse.RemittanceDetailAllResponseBuilder financeName(final String financeName) {
            this.financeName = financeName;
            return this;
        }

        public RemittanceDetailAllResponse.RemittanceDetailAllResponseBuilder remittanceName(final String remittanceName) {
            this.remittanceName = remittanceName;
            return this;
        }

        public RemittanceDetailAllResponse.RemittanceDetailAllResponseBuilder remittanceMoney(final Integer remittanceMoney) {
            this.remittanceMoney = remittanceMoney;
            return this;
        }

        public RemittanceDetailAllResponse.RemittanceDetailAllResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RemittanceDetailAllResponse.RemittanceDetailAllResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public RemittanceDetailAllResponse build() {
            return new RemittanceDetailAllResponse(this.remittanceId, this.memberName, this.financeName, this.remittanceName, this.remittanceMoney, this.createdAt, this.modifiedAt);
        }
    }
}