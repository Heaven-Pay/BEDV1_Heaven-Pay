package com.programmers.heavenpay.finance.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class FinanceCreateRequest {
    @ArbitraryAuthenticationPrincipal
    @NotBlank
    private Long id;

    @Pattern(regexp = "^[ㄱ-ㅎ|가-힣|a-z|A-Z|]{1,15}$", message = "이름은 공백없는 2~15자이어야 합니다")
    private String financeName;

    private String financeType;
}
