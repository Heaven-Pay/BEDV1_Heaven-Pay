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
    ACCOUNT_CREATE_SUCCESS(HttpStatus.OK, "계좌 생성 성공"),
    ACCOUNT_GET_SUCCESS(HttpStatus.OK, "계좌 단건 조회 성공"),
    ACCOUNT_GET_ALL_SUCCESS(HttpStatus.OK, "계좌 전체 조회 성공"),
    ACCOUNT_UPDATE_SUCCESS(HttpStatus.OK, "계좌 수정 성공"),
    ACCOUNT_DELETE_SUCCESS(HttpStatus.OK, "계좌 삭제 성공"),
    REMITTANCE_CREATE_SUCCESS(HttpStatus.OK, "송금하기 성공"),
    REMITTANCE_READ_SUCCESS(HttpStatus.OK, "송금 단건 조회 성공"),
    PRODUCT_INSERT_SUCCESS(HttpStatus.CREATED, "product 생성 성공"),
    PRODUCT_SEARCH_SUCCESS(HttpStatus.OK, "product 단건 조회 성공"),
    PRODUCT_DELETE_SUCCESS(HttpStatus.OK, "product 단건 삭제 성공"),
    PRODUCT_UPDATE_SUCCESS(HttpStatus.OK, "product 수정 성공"),
    ORDER_INSERT_SUCCESS(HttpStatus.CREATED, "신규 주문 생성 성공"),
    ORDER_UPDATE_SUCCESS(HttpStatus.OK, "주문 수정 성공"),
    ORDER_SEARCH_SUCCESS(HttpStatus.OK, "주문 단건 조회 성공"),
    WISH_INSERT_SUCCESS(HttpStatus.OK, "wish 생성 성공"),
    WISH_DELETE_SUCCESS(HttpStatus.OK, "wish 삭제 성공"),
    WISH_SEARCH_SUCCESS(HttpStatus.OK, "wish 검색 성공"),
    GIFT_ORDER_INSERT_SUCCESS(HttpStatus.CREATED, "신규 주문 생성 성공"),
    GIFT_ORDER_UPDATE_SUCCESS(HttpStatus.OK, "주문 수정 성공"),
    GIFT_ORDER_SEARCH_SUCCESS(HttpStatus.OK, "주문 단건 조회 성공"),
    MEMBER_CREATE_SUCCESS(HttpStatus.OK, "member 추가 성공"),
    MEMBER_UPDATE_SUCCESS(HttpStatus.OK, "member 수정 성공"),
    MEMBER_DELETE_SUCCESS(HttpStatus.OK, "member 삭제 성공"),
    MEMBER_READ_ONE_SUCCESS(HttpStatus.OK, "member 단건 조회 성공"),
    MEMBER_READ_ALL_SUCCESS(HttpStatus.OK, "member 전체 조회 성공"),
    REVIEW_INSERT_SUCCESS(HttpStatus.CREATED, "review 생성 성공"),
    REVIEW_DELETE_SUCCESS(HttpStatus.OK, "review 단건 삭제 성공"),
    REVIEW_SEARCH_SUCCESS(HttpStatus.OK, "review 단건 조회 성공"),
    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, "review 수정 성공"),
    FOLLOW_SUCCESS(HttpStatus.OK, "팔로잉 성공"),
    UNFOLLOW_SUCCESS(HttpStatus.OK, "언팔로잉 성공"),
    FOLLOW_FIND_SUCCESS(HttpStatus.OK, "팔로우 조회 성공"),
    FOLLOWER_FIND_SUCCESS(HttpStatus.OK, "팔로워 조회 성공"),
    POINT_WALLET_INSERT_SUCCESS(HttpStatus.OK, "포인트 지갑 생성 성공"),
    POINT_WALLET_UPDATE_SUCCESS(HttpStatus.OK, "포인트 지갑 수정 성공"),
    POINT_WALLET_DELETE_SUCCESS(HttpStatus.OK, "포인트 지갑 삭제 성공"),
    POINT_WALLET_READ_ONE_SUCCESS(HttpStatus.OK, "포인트 지갑 단건 조회 성공"),
    POINT_WALLET_READ_ALL_SUCCESS(HttpStatus.OK, "포인트 지갑 전체 조회 성공"),
    POINT_HISTORY_CREATE_SUCCESS(HttpStatus.OK, "포인트 내역 데이터 추가 성공"),
    POINT_HISTORY_UPDATE_SUCCESS(HttpStatus.OK, "포인트 내역 데이터 수정 성공"),
    POINT_HISTORY_DELETE_SUCCESS(HttpStatus.OK, "포인트 내역 데이터 삭제 성공"),
    POINT_HISTORY_READ_ONE_SUCCESS(HttpStatus.OK, "포인트 내역 데이터 단건 조회 성공"),
    POINT_HISTORY_READ_ALL_SUCCESS(HttpStatus.OK, "포인트 내역 데이터 전체 조회 성공"),
    PAYMENT_CREATE_SUCCESS(HttpStatus.OK, "결제 추가 성공"),
    PAYMENT_DELETE_SUCCESS(HttpStatus.OK, "결제 삭제 성공");

    private final HttpStatus status;
    private final String message;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}