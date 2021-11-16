package com.programmers.heavenpay.account.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AccountUpdateRequest {
    @NotNull(message = "아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @Pattern(regexp = "^.{1,50}$", message = "계좌 명칭은 1~50자이어야 합니다")
    private String title;

    @Pattern(regexp = "^.{0,100}$", message = "계좌 상세는 0~100자이어야 합니다")
    private String description;

    protected AccountUpdateRequest() {

    }

    public AccountUpdateRequest(Long memberId, String title, String description) {
        this.memberId = memberId;
        this.title = title;
        this.description = description;
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
}
