package com.programmers.heavenpay.account.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AccountCreateRequest {
    @NotNull(message = "아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @Pattern(regexp = "^.{1,50}$", message = "계좌 명칭은 1~50자이어야 합니다")
    private String title;

    @Pattern(regexp = "^.{0,100}$", message = "계좌 상세는 0~100자이어야 합니다")
    private String description;

    @NotBlank(message = "계좌 번호는 공백이 될 수 없습니다")
    private String number;

    @NotNull(message = "아이디를 입력하세요.")
    private Long financeId;

    protected AccountCreateRequest() {

    }

    public AccountCreateRequest(Long memberId, String title, String description, String number, Long financeId) {
        this.memberId = memberId;
        this.title = title;
        this.description = description;
        this.number = number;
        this.financeId = financeId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getNumber() {
        return number;
    }

    public Long getFinanceId() {
        return financeId;
    }
}
