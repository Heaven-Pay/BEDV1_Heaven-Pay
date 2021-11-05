package com.programmers.heavenpay.pointHistory.converter;

import com.programmers.heavenpay.member.entity.Member;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryCreateResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryDeleteResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryGetOneResponse;
import com.programmers.heavenpay.pointHistory.dto.response.PointHistoryUpdateResponse;
import com.programmers.heavenpay.pointHistory.entity.PointHistory;
import com.programmers.heavenpay.pointHistory.entity.vo.UsedAppType;
import org.springframework.stereotype.Component;

@Component
public class PointHistoryConverter {
    public PointHistory toPointHistoryEntity(Member member, String appType, String description, int usePoint) {
        return PointHistory.builder()
                .member(member)
                .usedApp(UsedAppType.of(appType))
                .description(description)
                .usePoint(usePoint)
                .build();
    }

    public PointHistoryCreateResponse toPointHistoryCreateResponse(PointHistory pointHistory) {
        return PointHistoryCreateResponse.builder()
                .id(pointHistory.getId())
                .build();
    }

    public PointHistoryGetOneResponse toPointHistoryFindResponse(PointHistory pointHistory) {
        return PointHistoryGetOneResponse.builder()
                .id(pointHistory.getId())
                .usedApp(pointHistory.getUsedApp())
                .description(pointHistory.getDescription())
                .usePoint(pointHistory.getUsePoint())
                .build();
    }

    public PointHistoryUpdateResponse toPointHistoryUpdateResponse(PointHistory pointHistory) {
        return PointHistoryUpdateResponse.builder()
                .id(pointHistory.getId())
                .usedApp(pointHistory.getUsedApp())
                .description(pointHistory.getDescription())
                .usePoint(pointHistory.getUsePoint())
                .build();
    }

    public PointHistoryDeleteResponse toPointHistoryDeleteResponse(PointHistory pointHistory) {
        return PointHistoryDeleteResponse.builder()
                .id(pointHistory.getId())
                .usedApp(pointHistory.getUsedApp())
                .description(pointHistory.getDescription())
                .usePoint(pointHistory.getUsePoint())
                .build();
    }
}
