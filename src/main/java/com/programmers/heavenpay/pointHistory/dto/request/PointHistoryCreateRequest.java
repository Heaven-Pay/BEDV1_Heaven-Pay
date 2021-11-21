package com.programmers.heavenpay.pointHistory.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.lang.annotation.Native;

public class PointHistoryCreateRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @Pattern(regexp = "^.{1,50}$", message = "사용한 앱 이름은 1~50자이어야 합니다")
    private String usedApp;

    @Pattern(regexp = "^.{0,100}$", message = "설명은 100자 내이어야 합니다.")
    private String description;

    @Positive()
    @Native()
    private Integer usePoint;

    protected PointHistoryCreateRequest() {
    }

    public PointHistoryCreateRequest(Long memberId, String usedApp, String description, Integer usePoint) {
        this.memberId = memberId;
        this.usedApp = usedApp;
        this.description = description;
        this.usePoint = usePoint;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getUsedApp() {
        return usedApp;
    }

    public String getDescription() {
        return description;
    }

    public Integer getUsePoint() {
        return usePoint;
    }
}
