package com.programmers.heavenpay.store.dto.response;

import java.time.LocalDateTime;

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

    public static StoreUpdateResponse.StoreUpdateResponseBuilder builder() {
        return new StoreUpdateResponse.StoreUpdateResponseBuilder();
    }

    public static class StoreUpdateResponseBuilder {
        private Long id;

        private String name;

        private String type;

        private String vendorCode;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        StoreUpdateResponseBuilder() {
        }

        public StoreUpdateResponse.StoreUpdateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public StoreUpdateResponse.StoreUpdateResponseBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public StoreUpdateResponse.StoreUpdateResponseBuilder type(final String type) {
            this.type = type;
            return this;
        }

        public StoreUpdateResponse.StoreUpdateResponseBuilder vendorCode(final String vendorCode) {
            this.vendorCode = vendorCode;
            return this;
        }

        public StoreUpdateResponse.StoreUpdateResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StoreUpdateResponse.StoreUpdateResponseBuilder modifiedAt(final LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public StoreUpdateResponse build() {
            return new StoreUpdateResponse(this.id, this.name, this.type, this.vendorCode, this.createdAt, this.modifiedAt);
        }
    }
}
