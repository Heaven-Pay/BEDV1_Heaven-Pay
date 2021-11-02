package com.programmers.heavenpay.pointHistory.dto.response;

import com.programmers.heavenpay.pointHistory.entity.vo.UsedAppType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PointHistoryUpdateResponse {
    private final Long id;
    private UsedAppType usedApp;
    private String description;
    private Integer usePoint;
}
