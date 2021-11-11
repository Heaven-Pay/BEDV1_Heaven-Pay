package com.programmers.heavenpay.product.dto.response;

public class ProductDeleteResponse {
    private final Long id;

    public ProductDeleteResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
