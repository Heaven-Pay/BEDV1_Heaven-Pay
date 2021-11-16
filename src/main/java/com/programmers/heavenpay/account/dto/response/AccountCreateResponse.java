package com.programmers.heavenpay.account.dto.response;

import java.time.LocalDateTime;

public class AccountCreateResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final String number;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private AccountCreateResponse(final Long id, final String title, final String description, final String number, final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.number = number;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static AccountCreateResponse.AccountCreateResponseBuilder builder() {
        return new AccountCreateResponse.AccountCreateResponseBuilder();
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

    public static class AccountCreateResponseBuilder {
        private Long id;
        private String title;
        private String description;
        private String number;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        private AccountCreateResponseBuilder() {
        }

        public AccountCreateResponse.AccountCreateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public AccountCreateResponse.AccountCreateResponseBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public AccountCreateResponse.AccountCreateResponseBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public AccountCreateResponse.AccountCreateResponseBuilder number(final String number) {
            this.number = number;
            return this;
        }

        public AccountCreateResponse.AccountCreateResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AccountCreateResponse.AccountCreateResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public AccountCreateResponse build() {
            return new AccountCreateResponse(this.id, this.title, this.description, this.number, this.createdAt, this.modifiedAt);
        }
    }
}
