package com.programmers.heavenpay.remittance.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RemittanceCreateRequest {
    @NotNull(message = "아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @NotNull(message = "계좌 내역은 공백이 아니여야 합니다")
    private Long accountId;

    @NotNull(message = "은행 정보는 공백이 아니여야 합니다")
    private Long financeId;

    @Pattern(regexp = "^[ㄱ-ㅎ|가-힣|a-z|A-Z|]{2,20}$", message = "이름은 공백없는 2~20자이어야 합니다")
    private String name;

    @NotBlank(message = "계좌 번호는 공백이 될 수 없습니다")
    private String number;

    @Min(value = 0)
    private Integer money;
}
