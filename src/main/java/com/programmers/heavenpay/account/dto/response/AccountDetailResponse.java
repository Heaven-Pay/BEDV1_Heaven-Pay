package com.programmers.heavenpay.account.dto.response;

import java.time.LocalDateTime;

public class AccountDetailResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final String number;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private AccountDetailResponse(final Long id, final String title, final String description, final String number, final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.number = number;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static AccountDetailResponse.AccountDetailResponseBuilder builder() {
        return new AccountDetailResponse.AccountDetailResponseBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getNumber() {
        return this.number;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return this.modifiedAt;
    }

    public static class AccountDetailResponseBuilder {
        private Long id;
        private String title;
        private String description;
        private String number;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        AccountDetailResponseBuilder() {
        }

        public AccountDetailResponse.AccountDetailResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public AccountDetailResponse.AccountDetailResponseBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public AccountDetailResponse.AccountDetailResponseBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public AccountDetailResponse.AccountDetailResponseBuilder number(final String number) {
            this.number = number;
            return this;
        }

        public AccountDetailResponse.AccountDetailResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AccountDetailResponse.AccountDetailResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public AccountDetailResponse build() {
            return new AccountDetailResponse(this.id, this.title, this.description, this.number, this.createdAt, this.modifiedAt);
        }
    }
}
