package com.programmers.heavenpay.finance.entity.vo;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;

import java.util.Arrays;

public enum FinanceType {
    BANK("은행"),
    STOCK("증권");

    private final String financeType;

    FinanceType(String financeType) {
        this.financeType = financeType;
    }

    public static FinanceType of(String inputFinanceType) {
        return Arrays.stream(FinanceType.values())
                .filter(f -> f.financeType.equals(inputFinanceType))
                .findAny()
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_FINANCE_TYPE));
    }

    public String getFinanceType() {
        return financeType;
    }
}
