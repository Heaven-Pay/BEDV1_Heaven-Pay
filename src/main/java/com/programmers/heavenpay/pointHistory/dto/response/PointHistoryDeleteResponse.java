package com.programmers.heavenpay.pointHistory.dto.response;

import com.programmers.heavenpay.pointHistory.entity.vo.UsedAppType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PointHistoryDeleteResponse {
    private final Long id;
    private final UsedAppType usedApp;
    private final String description;
    private final Integer usePoint;
}
