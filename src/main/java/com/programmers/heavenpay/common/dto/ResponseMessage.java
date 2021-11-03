package com.programmers.heavenpay.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseMessage {
    FINANCE_CREATE_SUCCESS(HttpStatus.CREATED, "금융 데이터 생성 성공입니다."),
    FINANCE_UPDATE_SUCCESS(HttpStatus.OK, "금융 데이터 수정 성공입니다."),
    FINANCE_READ_SUCCESS(HttpStatus.OK, "금융 데이터 단건 조회 성공입니다."),
    FINANCE_READ_ALL_SUCCESS(HttpStatus.OK, "금융 데이터 전체 조회 성공입니다."),
    FINANCE_DELETE_SUCCESS(HttpStatus.OK, "금융 데이터 삭제 성공입니다."),
    STORE_INSERT_SUCCESS(HttpStatus.CREATED, "store 추가 성공"),
    STORE_DELETE_SUCCESS(HttpStatus.OK, "store 단건 삭제 성공"),
    STORE_UPDATE_SUCCESS(HttpStatus.OK, "store 수정 성공"),
    STORE_SEARCH_SUCCESS(HttpStatus.OK, "store 조회 성공"),
    PRODUCT_INSERT_SUCCESS(HttpStatus.CREATED, "product 생성 성공"),
    PRODUCT_SEARCH_SUCCESS(HttpStatus.OK, "product 단건 조회 성공"),
    PRODUCT_DELETE_SUCCESS(HttpStatus.OK, "product 단건 삭제 성공"),
    PRODUCT_UPDATE_SUCCESS(HttpStatus.OK, "product 수정 성공"),
    ORDER_INSERT_SUCCESS(HttpStatus.CREATED, "신규 주문 생성 성공"),
    ORDER_UPDATE_SUCCESS(HttpStatus.OK, "주문 수정 성공"),
    ORDER_SEARCH_SUCCESS(HttpStatus.OK, "주문 단건 조회 성공"),
    MEMBER_INSERT_SUCCESS(HttpStatus.OK, "member 추가 성공"),
    MEMBER_UPDATE_SUCCESS(HttpStatus.OK, "member 수정 성공"),
    MEMBER_DELETE_SUCCESS(HttpStatus.OK, "member 삭제 성공"),
    MEMBER_FIND_SUCCESS(HttpStatus.OK, "member 조회 성공");

    private final HttpStatus status;
    private final String message;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}