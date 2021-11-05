package com.programmers.heavenpay.pointHistory.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.lang.annotation.Native;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
}
