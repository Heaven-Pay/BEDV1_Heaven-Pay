package com.programmers.heavenpay.account.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccountGetRequest {
    private Long memberId;
}
