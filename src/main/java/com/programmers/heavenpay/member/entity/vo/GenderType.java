package com.programmers.heavenpay.member.entity.vo;

import com.programmers.heavenpay.error.ErrorMessage;
import com.programmers.heavenpay.error.exception.NotExistsException;
import lombok.Getter;

import java.util.Arrays;

public enum GenderType {
    MALE("남성"),
    FEMALE("여성"),
    DEFAULT("미입력");

    private final String typeStr;

    GenderType(String typeStr) {
        this.typeStr = typeStr;
    }

    public static GenderType of(String genderType) {
        return Arrays.stream(GenderType.values())
                .filter(v -> v.typeStr.equals(genderType))
                .findFirst()
                .orElseThrow(() -> new NotExistsException(ErrorMessage.NOT_EXIST_GENDER_TYPE));
    }

    public String getTypeStr() {
        return typeStr;
    }
}
