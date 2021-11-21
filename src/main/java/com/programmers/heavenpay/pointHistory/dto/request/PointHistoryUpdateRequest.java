package com.programmers.heavenpay.pointHistory.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.Pattern;

public class PointHistoryUpdateRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @Pattern(regexp = "^.{0,100}$", message = "설명은 100자 내이어야 합니다.")
    private String description;

    private Integer usePoint;

    protected PointHistoryUpdateRequest() {
    }

    public PointHistoryUpdateRequest(Long memberId, String description, Integer usePoint) {
        this.memberId = memberId;
        this.description = description;
        this.usePoint = usePoint;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getDescription() {
        return description;
    }

    public Integer getUsePoint() {
        return usePoint;
    }
}
