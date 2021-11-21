package com.programmers.heavenpay.pointHistory.dto.response;

import com.programmers.heavenpay.pointHistory.entity.vo.UsedAppType;

public class PointHistoryUpdateResponse {
    private final Long id;
    private final UsedAppType usedApp;
    private final String description;
    private final Integer usePoint;

    public PointHistoryUpdateResponse(Long id, UsedAppType usedApp, String description, Integer usePoint) {
        this.id = id;
        this.usedApp = usedApp;
        this.description = description;
        this.usePoint = usePoint;
    }

    public Long getId() {
        return id;
    }

    public UsedAppType getUsedApp() {
        return usedApp;
    }

    public String getDescription() {
        return description;
    }

    public Integer getUsePoint() {
        return usePoint;
    }

    public static PointHistoryUpdateResponse.PointHistoryUpdateResponseBuilder builder() {
        return new PointHistoryUpdateResponse.PointHistoryUpdateResponseBuilder();
    }

    public static class PointHistoryUpdateResponseBuilder {
        private Long id;
        private UsedAppType usedApp;
        private String description;
        private Integer usePoint;

        private PointHistoryUpdateResponseBuilder() {
        }

        public PointHistoryUpdateResponse.PointHistoryUpdateResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PointHistoryUpdateResponse.PointHistoryUpdateResponseBuilder usedApp(final UsedAppType usedApp) {
            this.usedApp = usedApp;
            return this;
        }

        public PointHistoryUpdateResponse.PointHistoryUpdateResponseBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public PointHistoryUpdateResponse.PointHistoryUpdateResponseBuilder usePoint(final Integer usePoint) {
            this.usePoint = usePoint;
            return this;
        }

        public PointHistoryUpdateResponse build() {
            return new PointHistoryUpdateResponse(this.id, this.usedApp, this.description, this.usePoint);
        }
    }
}
