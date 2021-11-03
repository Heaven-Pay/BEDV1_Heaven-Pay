package com.programmers.heavenpay.remittance.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RemittanceCreateResponse {
    private final Long remittanceId;
    private final String memberName;
    private final String financeName;
    private final String remittanceName;
    private final String remittanceNumber;
    private final Integer remittanceMoney;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
