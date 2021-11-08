package com.programmers.heavenpay.store.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class StoreUpdateResponse {
    private final Long id;

    private final String name;

    private final String type;

    private final String vendorCode;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    public StoreUpdateResponse(Long id, String name, String type, String vendorCode, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.vendorCode = vendorCode;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}
