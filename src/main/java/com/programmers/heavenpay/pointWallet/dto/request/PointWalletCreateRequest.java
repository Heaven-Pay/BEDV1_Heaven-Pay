package com.programmers.heavenpay.pointWallet.dto.request;

import com.programmers.heavenpay.annotation.ArbitraryAuthenticationPrincipal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointWalletCreateRequest {
    @NotNull(message = "고객 아이디를 입력하세요.")
    @ArbitraryAuthenticationPrincipal
    private Long memberId;

    @NotNull(message = "계좌정보를 입력하세요.")
    private Long accountId;

    @PositiveOrZero
    private Integer walletPoint;
}
