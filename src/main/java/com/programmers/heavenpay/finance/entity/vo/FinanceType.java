package com.programmers.heavenpay.finance.entity.vo;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FinanceType {
    BANK("은행"),
    STOCK("증권");

    private final String financeType;

    public static FinanceType of(String inputFinanceType) {
        return Arrays.stream(FinanceType.values())
                .filter(f -> f.financeType.equals(inputFinanceType))
                .findAny()
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_FINANCE_TYPE));
    }
}
