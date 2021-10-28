package com.programmers.heavenpay.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseMessage {
    STORE_INSERT_SUCCESS(HttpStatus.CREATED, "store 추가 성공"),
    STORE_DELETE_SUCCESS(HttpStatus.OK, "store 단건 삭제 성공"),
    STORE_UPDATE_SUCCESS(HttpStatus.OK, "store 수정 성공"),
    STORE_SEARCH_SUCCESS(HttpStatus.OK, "store 조회 성공");

    private final HttpStatus status;
    private final String message;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}