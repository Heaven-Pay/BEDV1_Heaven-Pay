package com.programmers.heavenpay.account.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccountUpdateRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @Pattern(regexp = "^.{1,50}$", message = "계좌 명칭은 1~50자이어야 합니다")
    private String title;

    @Pattern(regexp = "^.{0,100}$", message = "계좌 상세는 0~100자이어야 합니다")
    private String description;
}
