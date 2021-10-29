package com.programmers.heavenpay.finance.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
@Builder
public class FinanceCreateRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @Pattern(regexp = "^[ㄱ-ㅎ|가-힣|a-z|A-Z|]{1,15}$", message = "금융기관 명칭은 공백없는 2~15자이어야 합니다")
    private String financeName;

    private String financeType;
}
