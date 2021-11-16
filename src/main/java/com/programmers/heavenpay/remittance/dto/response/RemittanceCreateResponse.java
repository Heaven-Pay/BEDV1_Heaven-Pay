package com.programmers.heavenpay.remittance.dto.response;

import java.time.LocalDateTime;

public class RemittanceCreateResponse {
    private final Long remittanceId;
    private final String memberName;
    private final String financeName;
    private final String remittanceName;
    private final String remittanceNumber;
    private final Integer remittanceMoney;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private RemittanceCreateResponse(final Long remittanceId, final String memberName, final String financeName, final String remittanceName, final String remittanceNumber, final Integer remittanceMoney, final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.remittanceId = remittanceId;
        this.memberName = memberName;
        this.financeName = financeName;
        this.remittanceName = remittanceName;
        this.remittanceNumber = remittanceNumber;
        this.remittanceMoney = remittanceMoney;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static RemittanceCreateResponse.RemittanceCreateResponseBuilder builder() {
        return new RemittanceCreateResponse.RemittanceCreateResponseBuilder();
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

    public String getRemittanceNumber() {
        return this.remittanceNumber;
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

    public static class RemittanceCreateResponseBuilder {
        private Long remittanceId;
        private String memberName;
        private String financeName;
        private String remittanceName;
        private String remittanceNumber;
        private Integer remittanceMoney;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        private RemittanceCreateResponseBuilder() {
        }

        public RemittanceCreateResponse.RemittanceCreateResponseBuilder remittanceId(final Long remittanceId) {
            this.remittanceId = remittanceId;
            return this;
        }

        public RemittanceCreateResponse.RemittanceCreateResponseBuilder memberName(final String memberName) {
            this.memberName = memberName;
            return this;
        }

        public RemittanceCreateResponse.RemittanceCreateResponseBuilder financeName(final String financeName) {
            this.financeName = financeName;
            return this;
        }

        public RemittanceCreateResponse.RemittanceCreateResponseBuilder remittanceName(final String remittanceName) {
            this.remittanceName = remittanceName;
            return this;
        }

        public RemittanceCreateResponse.RemittanceCreateResponseBuilder remittanceNumber(final String remittanceNumber) {
            this.remittanceNumber = remittanceNumber;
            return this;
        }

        public RemittanceCreateResponse.RemittanceCreateResponseBuilder remittanceMoney(final Integer remittanceMoney) {
            this.remittanceMoney = remittanceMoney;
            return this;
        }

        public RemittanceCreateResponse.RemittanceCreateResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RemittanceCreateResponse.RemittanceCreateResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public RemittanceCreateResponse build() {
            return new RemittanceCreateResponse(this.remittanceId, this.memberName, this.financeName, this.remittanceName, this.remittanceNumber, this.remittanceMoney, this.createdAt, this.modifiedAt);
        }
    }
}