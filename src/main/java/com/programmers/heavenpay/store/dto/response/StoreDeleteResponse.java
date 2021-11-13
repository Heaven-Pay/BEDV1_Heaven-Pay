package com.programmers.heavenpay.store.dto.response;

public class StoreDeleteResponse {
    private final Long id;

    public StoreDeleteResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
