package com.programmers.heavenpay.finance.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class FinanceCreateRequest {
    @NotNull(message = "아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @Pattern(regexp = "^[ㄱ-ㅎ|가-힣|a-z|A-Z|]{1,15}$", message = "금융기관 명칭은 공백없는 2~15자이어야 합니다")
    private String financeName;

    @NotNull(message = "금융 타입을 입력하세요")
    private String financeType;

    protected FinanceCreateRequest() {

    }

    public FinanceCreateRequest(Long memberId, String financeName, String financeType) {
        this.memberId = memberId;
        this.financeName = financeName;
        this.financeType = financeType;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getFinanceName() {
        return financeName;
    }

    public String getFinanceType() {
        return financeType;
    }
}