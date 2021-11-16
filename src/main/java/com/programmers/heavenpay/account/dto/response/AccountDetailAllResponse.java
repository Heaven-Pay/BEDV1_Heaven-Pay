package com.programmers.heavenpay.account.dto.response;

import java.time.LocalDateTime;

public class AccountDetailAllResponse {
    private final Long id;
    private final String title;
    private final String number;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private AccountDetailAllResponse(final Long id, final String title, final String number, final LocalDateTime createdAt, final LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static AccountDetailAllResponse.AccountDetailAllResponseBuilder builder() {
        return new AccountDetailAllResponse.AccountDetailAllResponseBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
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

    public static class AccountDetailAllResponseBuilder {
        private Long id;
        private String title;
        private String number;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        private AccountDetailAllResponseBuilder() {
        }

        public AccountDetailAllResponse.AccountDetailAllResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public AccountDetailAllResponse.AccountDetailAllResponseBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public AccountDetailAllResponse.AccountDetailAllResponseBuilder number(final String number) {
            this.number = number;
            return this;
        }

        public AccountDetailAllResponse.AccountDetailAllResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AccountDetailAllResponse.AccountDetailAllResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public AccountDetailAllResponse build() {
            return new AccountDetailAllResponse(this.id, this.title, this.number, this.createdAt, this.modifiedAt);
        }
    }
}