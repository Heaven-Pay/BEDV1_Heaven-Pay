package com.programmers.heavenpay.pointHistory.entity.vo;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;

import java.util.Arrays;

public enum UsedAppType {
    SERIES("시리즈"),
    WEBTOON("웹툰"),
    SHOPPING("쇼핑"),
    GIFT("선물하기"),
    EVENT("이벤트"),
    DEFAULT("미입력");

    private final String typeStr;

    UsedAppType(String typeStr) {
        this.typeStr = typeStr;
    }

    public static UsedAppType of(String usedAppType) {
        return Arrays.stream(UsedAppType.values())
                .filter(v -> v.typeStr.equals(usedAppType))
                .findFirst()
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_USED_APP_TYPE));
    }

    public String getTypeStr() {
        return typeStr;
    }
}
