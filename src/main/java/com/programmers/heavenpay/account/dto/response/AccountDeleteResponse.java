package com.programmers.heavenpay.account.dto.response;

public class AccountDeleteResponse {
    private final Long id;

    public AccountDeleteResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
