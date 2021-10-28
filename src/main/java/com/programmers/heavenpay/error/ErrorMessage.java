package com.programmers.heavenpay.error;

import com.programmers.heavenpay.error.exception.NotDefinitionException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public enum ErrorMessage {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "정의되지 않은 서버 에러"),
    NOT_EXIST_MEMBER_ID(HttpStatus.NOT_FOUND, "존재하지 않는 Member Id"),
    NOT_EXIST_GENDER_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 성별(gender) 값"),
    NOT_EXIST_STORE_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 Store Type"),
    NOT_EXIST_STORE(HttpStatus.NOT_FOUND, "존재하지 않는 Store"),
    NOT_EXIST_FINANCE_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 금융 조건"),
    DUPLICATION_FINANCE_NAME(HttpStatus.BAD_REQUEST, "중복된 이름"),
    NOT_EXIST_FINANCE(HttpStatus.NOT_FOUND, "존재하지 않는 금융 정보"),
    ALREADY_EXISTS_VENDOR_CODE(HttpStatus.ALREADY_REPORTED, "이미 존재하는 사업자번호");

    private final HttpStatus status;
    private final String message;

    ErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorMessage of(String errorMessage) {
        return Arrays.stream(values())
                .filter(e -> e.message.equals(errorMessage))
                .findFirst()
                .orElseThrow(() -> new NotDefinitionException(ErrorMessage.INTERNAL_SERVER_ERROR));
    }
}
