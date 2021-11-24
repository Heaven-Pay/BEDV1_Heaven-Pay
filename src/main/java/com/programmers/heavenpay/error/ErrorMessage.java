package com.programmers.heavenpay.error;

import com.programmers.heavenpay.error.exception.NotDefinitionException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum ErrorMessage {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "정의되지 않은 서버 에러"),
    NOT_EXIST_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 Member Id"),
    NOT_EXIST_GENDER_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 성별(gender) 값"),
    NOT_EXIST_POINT_HISTORY(HttpStatus.NOT_FOUND, "존재하지 않는 PointHistory Id"),
    NOT_EXIST_USED_APP_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 사용 앱(usedApp) 타입"),
    NOT_EXIST_STORE_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 Store Type"),
    NOT_EXIST_STORE(HttpStatus.NOT_FOUND, "존재하지 않는 Store"),
    NOT_EXIST_FINANCE_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 금융 조건"),
    DUPLICATION_FINANCE_NAME(HttpStatus.BAD_REQUEST, "중복된 이름"),
    NOT_EXIST_FINANCE(HttpStatus.NOT_FOUND, "존재하지 않는 금융 정보"),
    ALREADY_EXISTS_VENDOR_CODE(HttpStatus.ALREADY_REPORTED, "이미 존재하는 사업자번호"),
    NOT_EXIST_ACCOUNT(HttpStatus.BAD_REQUEST, "존재하지 않는 계좌입니다."),
    NOT_EXIST_REMITTANCE(HttpStatus.NOT_FOUND, "존재하지 않는 송금 정보"),
    NOT_ENOUGH_STOCK(HttpStatus.BAD_REQUEST, "상품 수량 부족"),
    NOT_EXIST_PRODUCT_CATEGORY(HttpStatus.BAD_REQUEST, "존재하지 않는 상품 카테고리"),
    NOT_EXIST_PRODUCT(HttpStatus.BAD_REQUEST, "존재하지 않는 상품"),
    MISMATCH_BETWEEN_PRODUCT_AND_STORE(HttpStatus.BAD_REQUEST, "상품과 소토어가 불일치"),
    NOT_EXIST_GIFT_ORDER_STATUS(HttpStatus.NOT_FOUND, "존재하지 않는 주문 상태"),
    NOT_EXIST_GIFT_ORDER(HttpStatus.NOT_FOUND, "존재하지 않는 주문"),
    NOT_EXIST_REVIEW(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰 ID"),
    NOT_EXIST_PRODUCT_ID(HttpStatus.NOT_FOUND, "존재하지 않는 상품 ID"),
    NOT_EXIST_POINT_WALLET(HttpStatus.NOT_FOUND, "존재하지 않는 포인트 지갑 ID"),
    NOT_EXIST_TARGET_MEMBER_ID(HttpStatus.NOT_FOUND, "존재하지 않는 targetMemberId"),
    NOT_EXIST_PAYMENT(HttpStatus.NOT_FOUND, "존재하지 않는 결제 ID"),
    NOT_ENOUGH_POINT(HttpStatus.PRECONDITION_FAILED, "금액이 부족합니다");

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

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
