package com.programmers.heavenpay.member.dto.response;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class MemberCreateResponse {
    private final Long id;
}
