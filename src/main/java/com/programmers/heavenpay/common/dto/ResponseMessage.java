package com.programmers.heavenpay.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseMessage {
    RESPONSE_EXAMPLE(HttpStatus.BAD_REQUEST, "예시 데이터입니다."),
    FINANCE_CREATE_SUCCESS(HttpStatus.CREATED, "금융 데이터 생성 성공입니다.");

    private final HttpStatus status;
    private final String message;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}