package com.programmers.heavenpay.pointHistory.dto.response;

import com.programmers.heavenpay.pointHistory.entity.vo.UsedAppType;

public class PointHistoryDeleteResponse {
    private final Long id;
    private final UsedAppType usedApp;
    private final String description;
    private final Integer usePoint;

    public PointHistoryDeleteResponse(Long id, UsedAppType usedApp, String description, Integer usePoint) {
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

    public static PointHistoryDeleteResponse.PointHistoryDeleteResponseBuilder builder() {
        return new PointHistoryDeleteResponse.PointHistoryDeleteResponseBuilder();
    }

    public static class PointHistoryDeleteResponseBuilder {
        private Long id;
        private UsedAppType usedApp;
        private String description;
        private Integer usePoint;

        private PointHistoryDeleteResponseBuilder() {
        }

        public PointHistoryDeleteResponse.PointHistoryDeleteResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PointHistoryDeleteResponse.PointHistoryDeleteResponseBuilder usedApp(final UsedAppType usedApp) {
            this.usedApp = usedApp;
            return this;
        }

        public PointHistoryDeleteResponse.PointHistoryDeleteResponseBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public PointHistoryDeleteResponse.PointHistoryDeleteResponseBuilder usePoint(final Integer usePoint) {
            this.usePoint = usePoint;
            return this;
        }

        public PointHistoryDeleteResponse build() {
            return new PointHistoryDeleteResponse(this.id, this.usedApp, this.description, this.usePoint);
        }
    }
}
