package com.programmers.heavenpay.member.dto.response;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import com.programmers.heavenpay.member.entity.vo.GenderType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class MemberUpdateResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String birth;
    private final GenderType gender;
}
