package com.programmers.heavenpay.account.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class AccountGetRequest {
    @ArbitraryAuthenticationPrincipal
    private Long memberId;
}
