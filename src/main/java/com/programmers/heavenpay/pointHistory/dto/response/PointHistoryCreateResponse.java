package com.programmers.heavenpay.pointHistory.dto.response;

public class PointHistoryCreateResponse {
    private final Long id;

    public PointHistoryCreateResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static PointHistoryCreateResponse.PointHistoryCreateResponseBuilder builder() {
        return new PointHistoryCreateResponse.PointHistoryCreateResponseBuilder();
    }

    public static class PointHistoryCreateResponseBuilder {
        private Long id;

        private PointHistoryCreateResponseBuilder() {
        }

        public PointHistoryCreateResponse.PointHistoryCreateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PointHistoryCreateResponse build() {
            return new PointHistoryCreateResponse(this.id);
        }
    }
}
