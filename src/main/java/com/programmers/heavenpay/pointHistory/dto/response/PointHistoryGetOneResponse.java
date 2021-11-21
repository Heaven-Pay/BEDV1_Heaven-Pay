package com.programmers.heavenpay.pointHistory.dto.response;

import com.programmers.heavenpay.pointHistory.entity.vo.UsedAppType;

public class PointHistoryGetOneResponse {
    private final Long id;
    private final UsedAppType usedApp;
    private final String description;
    private final Integer usePoint;

    public PointHistoryGetOneResponse(Long id, UsedAppType usedApp, String description, Integer usePoint) {
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

    public static PointHistoryGetOneResponse.PointHistoryGetOneResponseBuilder builder() {
        return new PointHistoryGetOneResponse.PointHistoryGetOneResponseBuilder();
    }

    public static class PointHistoryGetOneResponseBuilder {
        private Long id;
        private UsedAppType usedApp;
        private String description;
        private Integer usePoint;

        private PointHistoryGetOneResponseBuilder() {
        }

        public PointHistoryGetOneResponse.PointHistoryGetOneResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public PointHistoryGetOneResponse.PointHistoryGetOneResponseBuilder usedApp(final UsedAppType usedApp) {
            this.usedApp = usedApp;
            return this;
        }

        public PointHistoryGetOneResponse.PointHistoryGetOneResponseBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public PointHistoryGetOneResponse.PointHistoryGetOneResponseBuilder usePoint(final Integer usePoint) {
            this.usePoint = usePoint;
            return this;
        }

        public PointHistoryGetOneResponse build() {
            return new PointHistoryGetOneResponse(this.id, this.usedApp, this.description, this.usePoint);
        }
    }
}
